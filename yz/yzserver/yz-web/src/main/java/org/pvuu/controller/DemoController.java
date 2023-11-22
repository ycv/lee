package org.pvuu.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.pvuu.model.Person;
import org.pvuu.model.RespBean;
import org.pvuu.model.Stock;
import org.pvuu.service.DemoService;
import org.pvuu.utils.tool.SnowFlakeGenerateIdWorker;
import org.pvuu.utils.tools.Opslab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;

@RestController
@RequestMapping("/demo")
public class DemoController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * SpringAMQP帮我们封装好操作RabbitMQ的对象模板
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    DemoService demoService;

    @PostMapping("/addPerson")
    public RespBean addPerson(@RequestBody Person person) {
        if (demoService.addPerson(person) == 1) {
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @GetMapping("/demoSimpleDate")
    public String demoSimpleDate() {

        logger.warn("demoSimpleDate00123 : " + JSON.toJSONString("122133"));

        SimpleDateFormat dateFormat = new SimpleDateFormat(Opslab.DATETIME_FORMAT);
        logger.warn("dateFormat002 : " + JSON.toJSONString(dateFormat.format(System.currentTimeMillis())));

        String stringDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
        String stringDateOne = new SimpleDateFormat("yyyy-MM-01").format(new Date()).toString();
        logger.warn("stringDate : " + JSON.toJSONString(stringDate) + "    stringDateOne : "
            + JSON.toJSONString(stringDateOne));

        List<Stock> stockList = demoService.getDemoListByCodeStr("688722");
        logger.warn("demoSimpleDate002 : " + JSON.toJSONString(stockList.size()));
        logger.warn("demoSimpleDate002 : " + JSON.toJSONString(stockList));

        List<String> codeList = Arrays.asList("688722,601198".split(","));
        logger.warn("codeList11 : " + JSON.toJSONString(codeList));

        List<Stock> stockList2 = demoService.getDemoListByCodeStr(codeList);
        logger.warn("demoSimpleDate003 : " + JSON.toJSONString(stockList2.size()));

        logger.warn("demoSimpleDate013 : =====================================");

        SnowFlakeGenerateIdWorker worker1 = new SnowFlakeGenerateIdWorker(1, 1);
        SnowFlakeGenerateIdWorker worker2 = new SnowFlakeGenerateIdWorker(2, 1);
        SnowFlakeGenerateIdWorker worker3 = new SnowFlakeGenerateIdWorker(3, 1);
        for (int i = 0; i < 30; i++) {
            System.out.println("数据中心1，r雪花算法 ID：" + worker1.nextId());
            System.out.println("数据中心2，r雪花算法 ID：" + worker2.nextId());
            System.out.println("数据中心3，r雪花算法 ID：" + worker3.nextId());
        }

        return "115";
    }

    /**
     * 基本消息模型
     * 
     * RabbitMQ对消息进行接收、存储、转发。
     * 
     * 生产者：一个发送消息的用户应用程序。 消费者：等待并接收应用程序发送的消息。
     * 
     */
    @GetMapping("/send01")
    public void send01() {
        for (int i = 1; i <= 10; i++) {
            // 给指定routingKey发送消息
            // arg0: routingKey
            // arg1: 消息数据
            rabbitTemplate.convertAndSend("testQueue", "你好啊" + i);
        }
    }

    /**
     * Work消息模型（能者多劳）
     * 
     * 这种消费模型其实是基于基本消息模型的，只是对RabbitMQ的消息投递做了一个配置，给消费者投递时，一次不要投递过多的数据，以免造成性能浪费。
     * 
     */
    @GetMapping("/send02")
    public void send02() {
        for (int i = 1; i < 11; i++) {
            rabbitTemplate.convertAndSend("workQueue", "工作模式队列" + i);
        }
    }

}
