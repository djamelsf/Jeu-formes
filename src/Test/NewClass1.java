/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author mac
 */
public class NewClass1 {

    public static void main(String[] args) throws InterruptedException {
        long lStartTime = System.nanoTime();
        TimeUnit.SECONDS.sleep(5);
        long lEndTime = System.nanoTime();
        long output = lEndTime - lStartTime;

        System.out.println("Elapsed time in milliseconds: " + output / 1000000000);
    }

}
