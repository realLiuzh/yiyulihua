package com.yiyulihua.oss.utils;

import lombok.extern.slf4j.Slf4j;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import ws.schild.jave.*;


import java.io.*;

/**
 * 截取音频文件工具类
 */
@Slf4j
public class AudioUtils {
    /**
     * description: 裁剪音频
     *
     * @param fis   输入流对象
     * @param bt    歌曲的比特率
     * @param start 裁剪的起始时间
     * @param end   裁剪结束时间
     * @param fos   输出流对象
     * @return
     * @author sunbo
     * @date 2022/7/19 22:17
     */
    public static void cut(FileInputStream fis, FileOutputStream fos, int bt, int start, int end) throws IOException { //			      计算开始截取的字节
        int sta = bt * start * 1024 / 8;
        //计算结束截取的字节
        int en = bt * end * 1024 / 8;
        //定义接收到几个字符长度变量len和total累加器
        int len, total = 0;
        //定义接收字节的byte数组
        byte[] bz = new byte[1024];

        while ((len = fis.read(bz)) != -1) {
            //累加接收到的个数
            total += len;
            //判断字节是否小于要截取的字节数
            if (total < sta)
                continue;
            //判断字节是否大于要截取的字节数，大于则跳出循环
            if (total > en)
                break;
            //将bz数组中的字节输出到fos
            fos.write(bz, 0, len);
        }
    }


    public static void cut(File source, File target, Integer duration) throws IOException, CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException {
        FileInputStream inputStream = new FileInputStream(source);
        long size;
        if ((duration / 3) > 40) {
            // 比特率 * kbps ==> * kb/s ,1kb = 10^3b 区分 1KB = 2^10B
            size = getBitRate(source) * 1000 * 40 / 8;
        } else {
            long length = source.length();
            size = length / 3;
        }
        FileOutputStream outputStream = new FileOutputStream(target);
        byte[] buffer = new byte[1024];
        int total = 0;
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            total += len;
            if (total > size) {
                break;
            }
            outputStream.write(buffer, 0, len);
        }

        inputStream.close();
        outputStream.close();
    }

    /**
     * description: 获取时长
     *
     * @param file 音频
     * @return {@link Integer}
     * @author sunbo
     * @date 2022/7/19 23:27
     */
    public static Integer getTime(File file) throws CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException, IOException {
        AudioFile audioFile = AudioFileIO.read(file);
        return audioFile.getAudioHeader().getTrackLength();

    }

    public static Integer getBitRate(File file) throws CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException, IOException {
        AudioFile audioFile = AudioFileIO.read(file);
        return Integer.valueOf(audioFile.getAudioHeader().getBitRate());
    }


    /**
     * description: 音频格式转为mp3
     *
     * @param source 源文件
     * @param target 目标文件
     * @author sunbo
     * @date 2022/7/20 11:42
     */
    public static void ConvertingAnyAudioToMp3WithAProgressListener(File source, File target) {
        try {

            // Audio Attributes/音频编码属性
            AudioAttributes audio = new AudioAttributes();
            /*
             * 它设置将用于音频流转码的编解码器的名称。您必须从当前Encoder实例的getAudioEncoders（）方法返回的列表中选择一个值。否则，
             * 您可以传递AudioAttributes.DIRECT_STREAM_COPY特殊值，该值需要源文件中原始音频流的副本。
             */
            audio.setCodec("libmp3lame");
            /*
             * 它设置新重新编码的音频流的比特率值。如果未设置比特率值，编码器将选择默认值。该值应以每秒位数表示。例如，如果你想要128 kb /
             * s比特率，你应该调用setBitRate（new Integer（128000））。
             */
            audio.setBitRate(128000);
            /* 它设置将在重新编码的音频流中使用的音频通道的数量（1 =单声道，2 =立体声）。如果未设置通道值，编码器将选择默认值。 */
            audio.setChannels(2);
            /*
             * 它设置新重新编码的音频流的采样率。如果未设置采样率值，编码器将选择默认值。该值应以赫兹表示。例如，如果您想要类似CD的44100
             * Hz采样率，则应调用setSamplingRate（new Integer（44100））。
             */
            audio.setSamplingRate(44100);
            /* 可以调用此方法来改变音频流的音量。值256表示没有音量变化。因此，小于256的值是音量减小，而大于256的值将增加音频流的音量。 */
            audio.setVolume(256);

            // Encoding attributes/编码属性
            EncodingAttributes attrs = new EncodingAttributes();
            /*
             * 它设置将用于新编码文件的流容器的格式。给定参数表示格式名称。
             * 编码格式名称有效且仅在它出现在正在使用的Encoder实例的getSupportedEncodingFormats（）方法返回的列表中时才受支持。
             */
            attrs.setFormat("mp3");
            /* 它设置音频编码属性。如果从未调用过新的EncodingAttributes实例，或者给定参数为null，则编码文件中不会包含任何音频流 */
            attrs.setAudioAttributes(audio);
            /*
             * 它为转码操作设置偏移量。源文件将从其开始的偏移秒开始重新编码。例如，如果您想剪切源文件的前五秒，
             * 则应在传递给编码器的EncodingAttributes对象上调用setOffset（5）。
             */
            // attrs.setOffset(5F);
            /*
             * 它设置转码操作的持续时间。只有源的持续时间秒才会在目标文件中重新编码。例如，如果您想从源中提取和转码30秒的一部分，
             * 则应在传递给编码器的EncodingAttributes对象上调用setDuration（30）
             */
            // attrs.setDuration(30F);

            // Encode/编码
            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(source), target, attrs);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}


