package main.logger;

import liquibase.logging.LogMessageFilter;
import liquibase.logging.core.AbstractLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.logging.Level;

// 实现Liquibase的日志输出系统，映射到log4j
public class LiquibaseLogger extends AbstractLogger {

    private Logger logger = LogManager.getLogger(LiquibaseLogger.class.getName());

    public LiquibaseLogger(LogMessageFilter filter) {
        super(filter);
    }

    @Override
    public void close() throws Exception {
        super.close();
    }

    @Override
    public void log(Level level, String s, Throwable throwable) {
        if (Level.WARNING.equals(level)) {
            warning(s, throwable);
        } else if (Level.FINE.equals(level)) {
            debug(s, throwable);
        } else if (Level.INFO.equals(level)) {
            info(s, throwable);
        } else {
            logger.error(s);
        }
    }

    @Override
    public void warning(String message, Throwable e) {
        logger.warn(message, e);
    }

    @Override
    public void debug(String message, Throwable e) {
        logger.debug(message, e);
    }

    @Override
    public void info(String message, Throwable e) {
        logger.info(message, e);
    }
}
