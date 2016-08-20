package com.cargocn.poi;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;
import org.mozilla.universalchardet.UniversalDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncodingDetectUtil {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 获取文件编码
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public String getTxtFileEncoding(File file) throws IOException {
		String encoding = null;
		byte[] buf = new byte[4096];
		java.io.FileInputStream fis = null;
		UniversalDetector detector = null;
		try {
			fis = new java.io.FileInputStream(file); // (1)
			detector = new UniversalDetector(null);

			// (2)
			int nread;
			while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
				detector.handleData(buf, 0, nread);
			}
			//
			// (3)
			detector.dataEnd();
			// //
			// (4)
			encoding = detector.getDetectedCharset();
			if (encoding != null) {
				logger.debug("Detected encoding = " + encoding);
			} else {
				logger.debug("No encoding detected.");
			}
			//
			// (5)
			detector.reset();
			detector = null;
		} catch (Exception e) {
			logger.debug("", e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
					fis = null;
				} catch (Exception e) {
				}
			}
			if (detector != null) {
				detector.reset();
			}
		}
		if (encoding == null) {
			encoding = "GB18030";// 目前大多都是中文文件处理
		} else {
			if (encoding.toUpperCase().startsWith("KOI") || encoding.toUpperCase().startsWith("MAC")
					|| encoding.toUpperCase().startsWith("WINDOWS") || encoding.toUpperCase().startsWith("IBM855")) {
				// 目前没有韩文 或者mac编码 ，直接设置为中文，检测错误
				encoding = "GB18030";
			}
		}
		logger.debug("txt encoding " + encoding);
		return encoding;
	}

	public String encodingFileName(String fileName) {
		String returnFileName = StringUtils.trim(fileName);
		try {
			returnFileName = URLEncoder.encode(returnFileName, "UTF-8");
			returnFileName = StringUtils.replace(returnFileName, "+", "%20");
			// if (returnFileName.length() > 150) {
			// returnFileName = new String(fileName.getBytes(), "ISO8859-1");
			// returnFileName = StringUtils
			// .replace(returnFileName, " ", "%20");
			// }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			if (logger.isWarnEnabled()) {
				logger.info("Don't support this encoding ...");
			}
		}
		return returnFileName;
	}
}
