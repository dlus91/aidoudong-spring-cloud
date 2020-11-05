package com.aidoudong.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: dlus91
 * @Date: 2020/11/4 14:07
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class JMHExample01 {

    private final static String DATA = "DUMMY DATA";

    private List<String> arrayList;
    private List<String> linkedList;

    @Setup(Level.Iteration)
    public void setup(){
        this.arrayList = new ArrayList<>();
        this.linkedList = new LinkedList<>();
    }

//    @Benchmark
    public List<String> arrayListAdd(){
        this.arrayList.add(DATA);
        return this.arrayList;
    }

    @Benchmark
    public List<String> linkedListAdd(){
        this.linkedList.add(DATA);
        return this.linkedList;
    }

    public static void main(String[] args) throws RunnerException {
        final Options options =
                new OptionsBuilder()
                .include(JMHExample01.class.getSimpleName())
                .forks(1)
                .measurementIterations(2)
                .warmupIterations(2)
                .build();
        new Runner(options).run();

    }



}
