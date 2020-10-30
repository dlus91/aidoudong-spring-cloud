package com.aidoudong;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -Xms256m -Xmx512m -Xss512k -XX:MaxPermSize=50m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags
 * -XX:+UnlockCommercialFeatures -XX:+FlightRecorder
 */
@SpringBootApplication
public class ManagerApplicationApp implements WebMvcConfigurer {

    public static void main(String[] args) {
		new SpringApplicationBuilder(ManagerApplicationApp.class).run(args);
//		ProcessHandle.Info javaInfo = ProcessHandle.current().info();
//		System.out.println("java info "+javaInfo.toString());
//		printJdkVersionInfo(javaInfo.command().get());
		String jdkPath = "E:\\programmer\\tool\\jdk\\jdk8\\jdk\\bin\\java.exe";
		printJdkVersionInfo(jdkPath);
		System.out.println("=========application launched finished===========");

	}

    private static void printJdkVersionInfo(String javaPath) {
		InputStream is = null;
		BufferedReader br = null;
        try {
            ProcessBuilder pb = new ProcessBuilder(javaPath, "-version");
            Process ps = pb.start();
            is = ps.getErrorStream();
            br = new BufferedReader(new InputStreamReader(is));
            br.lines().forEach(str -> {
				String s = str;
				System.out.println(s);
            });
//            int exitCode = ps.waitFor();
//            System.out.println(exitCode);
        } catch (Exception e) {
			System.err.println("未指定jdk版本");
        } finally {
			try {
				is.close();
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }

}