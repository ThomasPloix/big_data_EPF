package org.epf.hadoop.colfil1;


import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;

import java.io.IOException;


public class RelationshipRecordReader extends RecordReader<LongWritable, Relationship> {
    private LineRecordReader lineRecordReader = new LineRecordReader();
    private Relationship relationship = new Relationship();
    private LongWritable key = new LongWritable();


   @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        lineRecordReader.initialize(split, context);
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        if (lineRecordReader.nextKeyValue()) {
            key.set(lineRecordReader.getCurrentKey().get());
            String[] ids = lineRecordReader.getCurrentValue().toString().split("<->");;
            if (ids.length >= 2) {
                relationship.setId1(ids[0]);
                relationship.setId2(ids[1]);
            }
            return true;
        }
        return false;
    }

    @Override
    public LongWritable getCurrentKey() throws IOException, InterruptedException {
        return key;
    }

    @Override
    public Relationship getCurrentValue() throws IOException, InterruptedException {
        return relationship;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        return lineRecordReader.getProgress();
    }

    @Override
    public void close() throws IOException {
        lineRecordReader.close();
    }
}
