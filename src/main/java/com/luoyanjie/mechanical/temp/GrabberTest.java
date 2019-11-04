package com.luoyanjie.mechanical.temp;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author luoyanjie
 * @date 2019-09-04 22:00
 * Coding happily every day!
 */
public class GrabberTest {
    public static String getVideoFirstImg(String path)throws Exception {
        Frame frame = null;
        //构造器支持InputStream，可以直接传MultipartFile.getInputStream()
        FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber(path);
        //开始播放
        fFmpegFrameGrabber.start();
        //获取视频总帧数
        int ftp = fFmpegFrameGrabber.getLengthInFrames();
        //指定第几帧
        fFmpegFrameGrabber.setFrameNumber(5);
        //获取指定第几帧的图片
        frame = fFmpegFrameGrabber.grabImage();
        //文件绝对路径+名字
        String fileName = "/Users/luoyanjie/first.jpg";
        File outPut = new File(fileName);
        ImageIO.write(FrameToBufferedImage(frame), "jpg", outPut);
        return fileName;
    }

    public static String getVideoLastImg(String path)throws Exception {
        Frame frame = null;
        FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber(path);
        //开始播放
        fFmpegFrameGrabber.start();
        //获取视频总帧数
        int ftp = fFmpegFrameGrabber.getLengthInFrames();
        //指定第几帧
        fFmpegFrameGrabber.setFrameNumber(ftp - 1);
        //获取指定第几帧的图片
        frame = fFmpegFrameGrabber.grabImage();
        String fileName = "/Users/luoyanjie/last.jpg";
        File outPut = new File(fileName);
        ImageIO.write(FrameToBufferedImage(frame), "jpg", outPut);
        return fileName;
    }

    public static BufferedImage FrameToBufferedImage(Frame frame) {
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bufferedImage = converter.getBufferedImage(frame);
        return bufferedImage;
    }

    public static void main(String[] args) throws Exception{
        String videoFileName = "/Users/luoyanjie/Downloads/test.mp4";
        System.out.println(getVideoLastImg(videoFileName));
        System.out.println(getVideoFirstImg(videoFileName));
    }
}
