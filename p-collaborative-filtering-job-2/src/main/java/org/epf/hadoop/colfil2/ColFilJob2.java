package org.epf.hadoop.colfil2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class ColFilJob2 extends Configured implements Tool {
    public int run(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: ColFilJob2 <input path> <output path>");
            System.exit(-1);
        }

        Configuration conf = getConf();
        Job job = Job.getInstance(conf, "Collaborative Filtering Job 2");

        job.setJarByClass(ColFilJob2.class);

        //Format d'entrée
        job.setInputFormatClass(org.apache.hadoop.mapreduce.lib.input.TextInputFormat.class);

        job.setMapperClass(Job2Mapper.class);
        job.setReducerClass(Job2Reducer.class);

        //format de sortie
        job.setOutputKeyClass(UserPair.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setNumReduceTasks(2);

         return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new Configuration(), new ColFilJob2(), args);
        System.exit(exitCode);
    }




}
