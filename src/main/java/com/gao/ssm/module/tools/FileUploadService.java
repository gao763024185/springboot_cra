package com.gao.ssm.module.tools;

import com.gao.ssm.module.pojo.FileResult;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
 * Created by 高保红 on 2017/2/21.
 */
@Service
public class FileUploadService {

    private static final Random r1 = new Random(System.currentTimeMillis()+1);
    private static final Random r2 = new Random(System.currentTimeMillis()+2);
    private static final Random r3 = new Random(System.currentTimeMillis()+3);

    public static FileResult picSubmit(HttpServletRequest request, String cate, MultipartFile oriFile,Integer id){

        String orginName = oriFile.getOriginalFilename();
        int len = orginName.lastIndexOf(".");
        String suffix = orginName.substring(len,orginName.length());
        String avatar = "files/picture/"+ DateUtil.format(new Date(),"yyyyMMdd/")+cate+"/";

        String realPath = request.getSession().getServletContext().getRealPath("/");
        String fileName = getRandomFileName()+suffix;
        try {
            File file = new File(FilenameUtils.concat(realPath,avatar));
            //判断文件是否存在
            if (!file.exists() && !file.isDirectory()){
                System.out.println(realPath+"目录不存在，需要创建");
                file.mkdirs();
            }
            //检出目标文件是否重复
            File folder = new File(FilenameUtils.concat(realPath,avatar));
            File destFile = null;
            destFile =  new File(folder,fileName);
            if (destFile.exists()) {
                destFile.delete();
                destFile = new File(folder, fileName);
            }

            oriFile.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileResult fileResult = new FileResult();
        fileResult.setId(id);
        fileResult.setOrginName(orginName);
        fileResult.setFileName(fileName);
        fileResult.setAvatarUrl(avatar+fileName);
        return fileResult;

    }
    private static String getRandomFileName() {
        int len = 5;
        int range = (int)Math.pow(10, len)-1;
        return StringUtils.leftPad(String.valueOf(r1.nextInt(range)+1), len, "0")
                + "-" + StringUtils.leftPad(String.valueOf(r2.nextInt(range)+1), len, "0")
                + "-" + StringUtils.leftPad(String.valueOf(r3.nextInt(range)+1), len, "0");
    }
}
