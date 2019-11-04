package com.luoyanjie.mechanical.util;

import org.apache.commons.lang3.StringUtils;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author luoyanjie
 * @date 2019-09-05 21:20
 * Coding happily every day!
 */
public class VideoUtil {
    private static final Integer DEFAULT_FRAME_NUMBER = 5;

    private static BufferedImage frameToBufferedImage(Frame frame) {
        Java2DFrameConverter converter = new Java2DFrameConverter();

        return converter.getBufferedImage(frame);
    }

    private static File getFirstImgBase(FFmpegFrameGrabber fFmpegFrameGrabber, Integer frameNumber) throws Exception {
        //开始播放
        fFmpegFrameGrabber.start();

        //获取视频总帧数
        int total = fFmpegFrameGrabber.getLengthInFrames();

        //指定第几帧
        fFmpegFrameGrabber.setFrameNumber(frameNumber < total ? frameNumber : (total - 1));

        //获取指定第几帧的图片
        Frame frame = fFmpegFrameGrabber.grabImage();

        //文件绝对路径+名字
        String fileName = "/root/zSky/run/temp_" + UUID.randomUUID().toString() + ".jpg";
        File outPut = new File(fileName);
        ImageIO.write(frameToBufferedImage(frame), "jpg", outPut);

        return outPut;
    }

    public static File getFirstImg(InputStream inputStream, Integer frameNumber) throws Exception {
        if (inputStream == null || frameNumber == null) return null;

        return getFirstImgBase(new FFmpegFrameGrabber(inputStream), frameNumber);
    }

    public static File getFirstImg(String file, Integer frameNumber) throws Exception {
        if (StringUtils.isEmpty(file) || frameNumber == null) return null;

        return getFirstImgBase(new FFmpegFrameGrabber(file), frameNumber);
    }

    public static File getFirstImg(String file) throws Exception {
        return getFirstImg(file, DEFAULT_FRAME_NUMBER);
    }

    public static File getFirstImg(InputStream inputStream) throws Exception {
        return getFirstImg(inputStream, DEFAULT_FRAME_NUMBER);
    }

    public static void main(String[] args) throws Exception {
        String videoFileName = "/Users/luoyanjie/Downloads/test.mp4";
        System.out.println(getFirstImg(videoFileName));
    }
}
