package com.demo.aop;

import com.demo.domain.Member;
import com.demo.domain.MemberLog;
import com.demo.security.SecurityUtil;
import com.demo.thread.ThreadLocalContext;
import com.demo.thread.ThreadLocalManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @author zacconding
 * @Date 2018-01-03
 * @GitHub : https://github.com/zacscoding
 */
@Aspect
@Component
public class LoggerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(LoggerAdvice.class);

    @AfterReturning("@annotation(MemberLoggings)")
    public void insertMembersLog(JoinPoint jp) {
        logger.debug("LoggerAdvice::insertMebersLog() is called");

        boolean clearedContext = false;
        try {
            MemberLoggings loggingAnnotation = getAdminLoggings(jp);
            boolean useComment = loggingAnnotation.useComment();
            clearedContext = (useComment == false);

            MemberLog log = getMemberLogFromMember(SecurityUtil.getMember());
            log.setType(loggingAnnotation.logType());
            log.setActionTime(LocalDateTime.now());

            ThreadLocalContext context = ThreadLocalManager.clearContext();
            if(useComment && context != null) {
                clearedContext = true;
                log.setComment(context.getMemberLogComment());
            }
            logger.debug("## [catch member`s action log] : {}" , log);
        }
        catch(NullPointerException e) {
            e.printStackTrace();
            logger.error("## failed to insert members log",e);
        }
        catch(Exception e) {
            e.printStackTrace();
            logger.error("## failed to insert members log",e);
        }
        finally {
            if(!clearedContext) {
                ThreadLocalManager.clearContext();
            }
        }
    }

    private MemberLoggings getAdminLoggings(JoinPoint jp) throws NullPointerException {
        MemberLoggings annotation = null;
        try {
            MethodSignature methodSignature = (MethodSignature) jp.getSignature();
            Method method = methodSignature.getMethod();
            annotation = method.getAnnotation(MemberLoggings.class);
        }
        catch (Exception e) {
            // ignore exceptions
        }
        return annotation;
    }

    private MemberLog getMemberLogFromMember(Member member) {
        if(member == null) {
            return null;
        }

        MemberLog log = new MemberLog();
        log.setIp(member.getIp());
        log.setLoginId(member.getLoginId());

        return log;
    }

}
