package org.epf.hadoop.colfil3;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class ColFilJob3  extends Configured implements Tool {
    public int run(String[]args) throws Exception {
        String inputPath = "";
        String outputPath = "";

        if (args.length != 2) {
            System.err.println("Usage: ColFilJob3 <input path> <output path>");
            System.exit(-1);
        }
        else {
            inputPath = args[0];
            outputPath = args[1];
        }

        Configuration conf = getConf();
        Job job = Job.getInstance(conf, "Collaborative Filtering Job 3");

        job.setJarByClass(ColFilJob3.class);

        job.setInputFormatClass(TextInputFormat.class);

        job.setMapperClass(Job3Mapper.class);
        job.setReducerClass(Job3Reducer.class);

        // Définir les types de sortie du Mapper
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(UserRecommendation.class);

        // Définir les types de sortie du Reducer
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setNumReduceTasks(1);

        // Définir les chemins d'entrée et de sortie
        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new Configuration(), new ColFilJob3(), args);
        System.exit(exitCode);
    }
}