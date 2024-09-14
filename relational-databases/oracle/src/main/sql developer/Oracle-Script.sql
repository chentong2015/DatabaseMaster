PROMPT Date Format is: YYYYMMDD HH24MISS
ACCEPT v_DateLimite_INPUT DATE FORMAT 'YYYYMMDD HH24MISS' DEFAULT '20210924 121010' -
PROMPT 'Enter date for selection (older sessions will be deleted) > '

PROMPT
PROMPT If step size is zero, a single final commit will be performed
ACCEPT n_CommitStep_INPUT NUMBER DEFAULT 0 -
PROMPT 'Enter commit step size (Default is 1000) > '

DECLARE
    v_DateLimite VARCHAR2(200);
    n_CommitStep NUMBER := 0;
    TYPE T_LOG_RECORD_KEY IS TABLE OF FUM_LOG.RECORD_KEY%TYPE
        INDEX BY PLS_INTEGER;
    t_AllSessionsToDelete T_LOG_RECORD_KEY;
    t_BatchedSessionsToDelete T_LOG_RECORD_KEY;

BEGIN
    v_DateLimite := TO_CHAR(TO_DATE('&v_DateLimite_INPUT', 'YYYYMMDD HH24MISS'), 'YYYY-MM-DD HH24:MI:SS');
    DBMS_OUTPUT.PUT_LINE('Date limite is: ' || v_DateLimite);
    DBMS_OUTPUT.NEW_LINE;

    n_CommitStep := TO_NUMBER('&n_CommitStep_INPUT');
    DBMS_OUTPUT.PUT_LINE('Commit step size is: ' || n_CommitStep);
    DBMS_OUTPUT.NEW_LINE;

    WITH 
        RECORDED_SESSION AS
        (
            SELECT RECORD_KEY SESSION_KEY, UPDATE_DATE CREATE_DATE
            FROM FUM_LOG
            WHERE LINK_TO = -1
            AND UPDATE_DATE < v_DateLimite
        ),
        SESSION_ROWS AS
        (
            SELECT S.SESSION_KEY, 
                NVL2(L.UPDATE_DATE, L.UPDATE_DATE, S.CREATE_DATE) UPDATE_DATE
            FROM RECORDED_SESSION S
                LEFT OUTER JOIN FUM_LOG L
                    ON L.LINK_TO = S.SESSION_KEY
        )
    SELECT SESSION_KEY
        BULK COLLECT INTO t_AllSessionsToDelete
    FROM SESSION_ROWS
    GROUP BY SESSION_KEY
    HAVING MAX(UPDATE_DATE) < v_DateLimite;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('An error occurred.');
        DBMS_OUTPUT.PUT_LINE('Error: ' || TO_CHAR(SQLERRM));
END;