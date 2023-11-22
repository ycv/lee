package org.pvuu.queue;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Customer {

    /**
     * @RabbitListener： 用于类上和方法上，用于类上时可以配合@RabbitHandler使用，本文不阐述；主要说说用于方法上，可以用于声明队列，用于绑定交换机和队列。
     *
     * queuesToDeclare：将队列绑定到默认交换机上，routeKey为队列名称。
     *
     * @Queue： 队列注解，value为队列名称
     * 
     * queuesToDeclare：支持多个队列，将队列绑定到默认交换机上，routeKey为队列名称。
     * 
     * @param msg 接收到的消息
     */
    @RabbitListener(queuesToDeclare = @Queue(value = "testQueue"))
    public void listener(String msg) {
        System.out.println("111-1 " + msg);
    }

    /**
     * 慢消费者，模拟性能比较差的服务器
     * 
     * @param msg
     */
    @RabbitListener(queuesToDeclare = @Queue(value = "workQueue"))
    public void listener02(String msg) {
        try {
            // 模拟执行每次任务需要1秒
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("慢SlowCustomer- ： " + msg);
    }

    /**
     * 快消费者，模拟性能比较好的服务器
     * 
     * @param msg
     */
    @RabbitListener(queuesToDeclare = @Queue(value = "workQueue"))
    public void listener021(String msg) {
        System.out.println("快FastCustomer- ： " + msg);
    }

}
