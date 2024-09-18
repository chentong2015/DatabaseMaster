// 申明脚本使用的参数，在运行时动态设置参数的具体值
// 脚本参数的传递需要通过Command Line，而非使用GUI界面 ！！
DECLARE
	@n_CurrRow          INT = 0,
	@n_MaxRow           INT = 0,
    @n_totalRowsDeleted INT = 0,
    @v_DateLimite       VARCHAR(200) = '$(DateLimite)',
    @n_CommitStep       INT = '$(CommitStep)';

IF REVERSE(@v_DateLimite) = ')etimiLetaD($' BEGIN
    PRINT 'Error: Parameter v_DateLimite must be initialized';
    RETURN;
END
IF REVERSE(@n_CommitStep) = ')petStimmoC($' BEGIN
    PRINT 'Error: Parameter v_DateLimite must be initialized';
    RETURN;
END

PRINT '/*********************************************/';
PRINT '/*  PURGE FUM_LOG SESSIONS                   */';
PRINT '/*********************************************/';
PRINT FORMAT(GETDATE(), 'dd/MM/yyyy HH:mm:ss') + ' Start of processing';
PRINT FORMAT(GETDATE(), 'dd/MM/yyyy HH:mm:ss') + ' Date limite      : ' + @v_DateLimite;
PRINT FORMAT(GETDATE(), 'dd/MM/yyyy HH:mm:ss') + ' Commit step size : ' + CAST(@n_CommitStep AS VARCHAR);

DROP TABLE IF EXISTS #SESSIONS_TO_DELETE;

// 指定特定表的生成规则
// 注意HAVING关键字的使用
WITH 
    RECORDED_SESSION AS(
        SELECT RECORD_KEY SESSION_KEY, UPDATE_DATE CREATE_DATE
        FROM FUM_LOG
        WHERE LINK_TO = -1
        AND UPDATE_DATE < @v_DateLimite
    ),
    SESSION_ROWS AS (
        SELECT S.SESSION_KEY, 
            CASE WHEN L.UPDATE_DATE IS NOT NULL THEN L.UPDATE_DATE ELSE S.CREATE_DATE END UPDATE_DATE
        FROM RECORDED_SESSION S
            LEFT OUTER JOIN FUM_LOG L ON L.LINK_TO = S.SESSION_KEY
    )
SELECT ROW_NUMBER() OVER (ORDER BY SESSION_KEY) AS NUM_ROW, SESSION_KEY, MAX(UPDATE_DATE) UPDATE_DATE
INTO #SESSIONS_TO_DELETE
FROM SESSION_ROWS
GROUP BY SESSION_KEY
HAVING MAX(UPDATE_DATE) < @v_DateLimite;