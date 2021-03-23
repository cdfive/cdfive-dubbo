package com.cdfive.learn.thread.threadsafe;

/**
 * @author cdfive
 */
public class ThreadSafeTest5 {

    public static void main(String[] args) throws InterruptedException {
        Item item = new Item(0, 0);
        Task task1 = new Task(item, true);
        Task task2 = new Task(item, false);
        task1.start();
        task2.start();
        task1.join();
        task2.join();

        System.out.println("item.i=" + item.i);
        System.out.println("item.j=" + item.j);
        System.out.println("main done");
    }

    public static class Task extends Thread {

        private Item item;

        private boolean flag;

        public Task(Item item, boolean flag) {
            this.item = item;
            this.flag = flag;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                if (flag) {
                    item.i++;
                } else {
                    item.j++;
                }
            }
        }
    }

    public static class Item {
        public Item(Integer i, Integer j) {
            this.i = i;
            this.j = j;
        }

        public Integer i;

        public Integer j;
    }
}
