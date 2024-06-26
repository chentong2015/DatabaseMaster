DELIMITER
//
-- 函数声明的命名规范：f_函数名
CREATE FUNCTION f_get_fullname(fname CHAR (250), lname CHAR (250)) RETURNS CHAR(250)
BEGIN
  DECLARE
fullname CHAR(250);
  SET
fullname=CONCAT(fname," -- ",lname);
RETURN fullname;
END
//
DELIMITER ;
-- 调用自定义的SQL Function
-- 使用drop来删除table
SELECT f_get_fullname("chen", "tong") AS MyName;
DROP FUNCTION f_get_fullname;


-- A stored Procedure is a prepared SQL code, can be reused for many times
-- 通过传递不同的参数，反复的执行同样的代码
DELIMITER
//
-- 存储过程命名规范：p_存储过程名
CREATE PROCEDURE myProcedure(username CHAR (10))
BEGIN
SELECT *
FROM spring_db.information
WHERE name = username;
END
//
DELIMITER ;
-- 调用自定义的SQL Procedure, 执行指定的SQL查询
-- 可以使用drop来删除procedure
CALL myProcedure("vitor");
DROP PROCEDURE IF EXISTS myProcedure //

