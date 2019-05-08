import java.io.*;

import org.springframework.util.StringUtils;

public class FileUtil
{
	
	public static void writeFile(Object obj, String dir, String fileName)
	{
		try
		{
			File fileDir = new File(dir);
			if (!fileDir.exists())
			{
				fileDir.mkdirs();
			}
			File file = new File(dir + "/" + fileName);
			if (!file.exists())
			{
				file.createNewFile();
			}
			
			FileOutputStream out = new FileOutputStream(file, false);
			String content = JacksonUtil.toJSon(obj);
			out.write(content.getBytes("utf-8"));
			out.close();
		} 
		catch (Exception e)
		{
			// 日志
			Constant.operateLogger.error("{}|{}|{}", "FileUtil/writeFile", "filePath=" + dir + "/" + fileName, e.getMessage());
		}
	}
	
	public static String readFile(String fileName) 
	{
		String detailRecord = "";
		BufferedReader reader = null;
		try
		{
			File file = new File(fileName);
			if (!file.exists())
			{
				return null;
			}
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while(!StringUtils.isEmpty(tempString = reader.readLine()))
			{
				detailRecord = detailRecord + tempString;
			}
		} 
		catch (Exception e)
		{
			Constant.operateLogger.error("{}|{}|{}", "FileUtil/readFile", "filePath=" + fileName, e.getMessage());
		}
		finally 
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				} 
				catch (IOException e)
				{
					Constant.operateLogger.error("{}|{}|{}", "FileUtil/readFile (reader.close())", "filePath=" + fileName, e.getMessage());
				}
			}
		}
		
		if (StringUtils.isEmpty(detailRecord))
		{
			return null;
		}
		else
		{
			return detailRecord;
		}
	}
	/**
	 * 读取文件内容，作为字符串返回
	 */
	public static String readFileAsString(String filePath) throws IOException {
		File file = new File(filePath);
		if (!file.exists()) {
			throw new FileNotFoundException(filePath);
		}

		if (file.length() > 1024 * 1024 * 1024) {
			throw new IOException("File is too large");
		}

		StringBuilder sb = new StringBuilder((int) (file.length()));
		// 创建字节输入流
		FileInputStream fis = new FileInputStream(filePath);
		// 创建一个长度为10240的Buffer
		byte[] bbuf = new byte[10240];
		// 用于保存实际读取的字节数
		int hasRead = 0;
		while ( (hasRead = fis.read(bbuf)) > 0 ) {
			sb.append(new String(bbuf, 0, hasRead));
		}
		fis.close();
		return sb.toString();
	}

	/**
	 * 根据文件路径读取byte[] 数组
	 */
	public static byte[] readFileByBytes(String filePath) throws IOException {
		File file = new File(filePath);
		if (!file.exists()) {
			throw new FileNotFoundException(filePath);
		} else {
			ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
			BufferedInputStream in = null;

			try {
				in = new BufferedInputStream(new FileInputStream(file));
				short bufSize = 1024;
				byte[] buffer = new byte[bufSize];
				int len1;
				while (-1 != (len1 = in.read(buffer, 0, bufSize))) {
					bos.write(buffer, 0, len1);
				}

				byte[] var7 = bos.toByteArray();
				return var7;
			} finally {
				try {
					if (in != null) {
						in.close();
					}
				} catch (IOException var14) {
					var14.printStackTrace();
				}

				bos.close();
			}
		}
	}
}
