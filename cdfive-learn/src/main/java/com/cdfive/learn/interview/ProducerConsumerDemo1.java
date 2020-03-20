package com.cdfive.learn.interview;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author cdfive
 */
public class ProducerConsumerDemo1 {

    public static class Warehouse {

        private int capacity;
        private int stock;

        private AtomicInteger atomStock = new AtomicInteger(0);

        public Warehouse(int capacity, int stock) {
            this.capacity = capacity;
            this.stock = stock;
        }

        public synchronized void produce(int count) {
//        public void produce(int count) {
//            try {
                if (false && stock >= capacity) {
                    System.out.println("produce " + count + "=>waiting");
//                    wait();
                } else {
                    int incrStock = false && stock + count >= capacity ? capacity - stock : count;
                    stock += incrStock;
                    atomStock.addAndGet(incrStock);
                    System.out.println("produce " + count + "=>" + incrStock + ",remain=" + stock);
//                    notifyAll();
                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

        public synchronized void consume(int count) {
//        public void consume(int count) {
//            try {
                if (false && stock <= 0) {
                    System.out.println("consume " + count + "=>waiting");
//                    wait();
                } else {
                    int decrStock = true || stock - count >= 0 ? count : stock;
                    stock -= decrStock;
                    atomStock.addAndGet(-decrStock);
                    System.out.println("consume " + count + "=>" + decrStock + ",remain=" + stock);
//                    notifyAll();
                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

        public int getCapacity() {
            return capacity;
        }

        public int getStock() {
            return stock;
        }

        public AtomicInteger getAtomStock() {
            return atomStock;
        }

        @Override
        public String toString() {
            return "Warehouse{" +
                    "capacity=" + capacity +
                    ", stock=" + stock +
                    '}';
        }
    }

    public static class Producer {
        private Warehouse warehouse;

        public Producer(Warehouse warehouse) {
            this.warehouse = warehouse;
        }

        public void produce(int count) {
            new Thread(new Runnable() {
                @Override
                public void run() {
//                    sleepMs(ThreadLocalRandom.current().nextInt(2000));
                    warehouse.produce(count);
                }
            }, "producer").start();
        }
    }

    public static class Consumer {
        private Warehouse warehouse;

        public Consumer(Warehouse warehouse) {
            this.warehouse = warehouse;
        }

        public void consume(int count) {
            new Thread(new Runnable() {
                @Override
                public void run() {
//                    sleepMs(ThreadLocalRandom.current().nextInt(2000));
                    warehouse.consume(count);
                }
            }, "consumer").start();
        }
    }

    private static void sleepMs(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Warehouse warehouse = new Warehouse(100, 0);

        Producer producer = new Producer(warehouse);
        Consumer consumer = new Consumer(warehouse);

        int shouldProduceCount = 0;
        int shouldConsumeCount = 0;
        Random random = new Random();
        for (int i = 0; i < 20000; i++) {
            boolean flag = ThreadLocalRandom.current().nextBoolean();
            int count = ThreadLocalRandom.current().nextInt(200);

//            boolean flag = random.nextBoolean();
//            int count = random.nextInt(200);
//            count = 1;
            if (flag) {
//                shouldProduceCount += Math.min(count, warehouse.getCapacity());
                shouldProduceCount += count;
                producer.produce(count);
            } else {
//                shouldConsumeCount += Math.min(count, warehouse.getCapacity());
                shouldConsumeCount += count;
                consumer.consume(count);
            }
        }

//        for (int i = 0; i < 10; i++) {
//            consumer.consume(20);
//            producer.produce(120);
//            consumer.consume(60);
//            consumer.consume(30);
//            consumer.consume(25);
//            consumer.consume(110);
//            producer.produce(30);
//            producer.produce(50);
//            producer.produce(200);
//            consumer.consume(70);
//        }

        TimeUnit.SECONDS.sleep(5);

        System.out.println("------------------------");
        System.out.println(warehouse);
        System.out.println("shouldStock(shouldProduceCount-shouldConsumeCount)=" + (shouldProduceCount - shouldConsumeCount));
        System.out.println("atomStock=" + warehouse.getAtomStock().get());

        System.out.println("main done");
    }
}
