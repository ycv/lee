package org.pvuu.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.pvuu.model.Employee;
import org.pvuu.model.MailConstants;
import org.pvuu.model.MailSendLog;
import org.pvuu.service.EmployeeService;
import org.pvuu.service.MailSendLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;

@Component
public class MailSendTask {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    MailSendLogService mailSendLogService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    EmployeeService employeeService;

    /**
     * 每20分钟执行一次
     */
    @Scheduled(cron = "0 */90 * * * ?")
    public void mailResendTask() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            List<MailSendLog> logs = mailSendLogService.getMailSendLogsByStatus();
            // logger.warn("mailResendTask1 ： " + JSON.toJSONString("aaas试试33"));
            // logger.warn("mailResendTask2 ： " + JSON.toJSONString(logs.size()));
            if (logs == null || logs.size() == 1) {
                logger.warn("mailResendTask5 ： " + JSON.toJSONString(simpleDateFormat.format(new Date())));
                return;
            }
            if (!CollectionUtils.isEmpty(logs)) {
                logs.forEach(mailSendLog -> {
                    logger.warn("mailResendTask3 ： " + JSON.toJSONString(mailSendLog));
                    logger.warn("mailResendTask4 ： " + JSON.toJSONString(mailSendLog.getCount()));
                    if (mailSendLog.getCount() >= 3) {
                        mailSendLogService.updateMailSendLogStatus(mailSendLog.getMsgId(), 2);// 直接设置该条消息发送失败
                    } else {
                        mailSendLogService.updateCount(mailSendLog.getMsgId(), new Date());
                        Employee emp = employeeService.getEmployeeById(mailSendLog.getEmpId());
                        rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME,
                            MailConstants.MAIL_ROUTING_KEY_NAME, emp, new CorrelationData(mailSendLog.getMsgId()));
                    }
                });
            }
        } catch (Exception e) {
            logger.warn("mailResendTask6 ： " + JSON.toJSONString("mailResendTask5mailResendTask6"));
            e.printStackTrace();
        }
    }

    /**
     * 每600秒执行一次
     */
    @Scheduled(cron = "0/9000 * * * * ?")
    public void demoTask() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM-ddHHmmss");
        // logger.warn("demoTask1-- ： " + JSON.toJSONString(simpleDateFormat.format(new Date())));
        // demoSimpleDate();
        try {
        } catch (Exception e) {
            logger.warn("demoTaskErr ： " + JSON.toJSONString("demoTask6"));
            e.printStackTrace();
        }
    }
}
