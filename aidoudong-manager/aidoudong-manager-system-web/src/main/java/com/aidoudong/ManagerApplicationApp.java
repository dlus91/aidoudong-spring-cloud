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

	InputStream is = null;
	BufferedReader br = null;

    public static void main(String[] args) throws IOException {
		new SpringApplicationBuilder(ManagerApplicationApp.class).run(args);
//		ProcessHandle.Info javaInfo = ProcessHandle.current().info();
//		System.out.println("java info "+javaInfo.toString());
//		printJdkVersionInfo(javaInfo.command().get());
		String jdkPath = "E:\\programmer\\tool\\jdk\\jdk8\\jdk\\bin\\java.exe";
		printJdkVersionInfo(jdkPath);
		System.out.println("=========application launched finished===========");

	}

    private static void printJdkVersionInfo(String javaPath) throws IOException {
		ProcessBuilder pb = new ProcessBuilder(javaPath, "-version");
		Process ps = pb.start();
		try(
			InputStream is = ps.getErrorStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
		){
			br.lines().forEach(str -> {
				String s = str;
				System.out.println(s);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}