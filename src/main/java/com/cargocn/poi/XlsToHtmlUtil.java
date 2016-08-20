package com.cargocn.poi;

/* ====================================================================
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==================================================================== */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * xls转换html
 * 
 * @author Ken Arnold, Industrious Media LLC
 */
public class XlsToHtmlUtil {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private ExcelToHTML toHtml;
	private String xlsFile;
	private String htmlFile;
	private boolean isXlsFile = true;

	public XlsToHtmlUtil(String xlsFile, String htmlFile) throws IOException {
		this.xlsFile = xlsFile;
		this.htmlFile = htmlFile;
		PrintWriter pw = new PrintWriter(new FileWriter(htmlFile));
		try {
			toHtml = ExcelToHTML.create(xlsFile, pw);
		} catch (IllegalArgumentException e) {
			// try save as html
			isXlsFile = false;
		} finally {
			if (pw != null && !isXlsFile) {
				try {
					pw.flush();
					pw.close();
					pw = null;
				} catch (Exception e) {
				}
			}
		}

	}

	public void printPage(boolean completeHtml) throws IOException {
		if (isXlsFile) {
			toHtml.setCompleteHTML(completeHtml);
			toHtml.printPage();
		} else {
			if (isHtmlFormat()) {
				copyFile();
			}
		}
	}

	private void copyFile() {
		InputStream fis = null;
		BufferedReader br = null;
		String line;
		OutputStream fos = null;
		BufferedWriter bw = null;
		File fi = null;
		try {
			fi = new File(xlsFile);
			fis = new FileInputStream(fi);
			fos = new FileOutputStream(htmlFile);
			String enc = procEnc((new EncodingDetectUtil()).getTxtFileEncoding(new File(xlsFile)));
			br = new BufferedReader(new InputStreamReader(fis, enc));
			bw = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));
			while ((line = br.readLine()) != null) {
				if (line != null && line.toLowerCase().contains("charset=gb2312")) {
					line = line.replace("charset=gb2312", "charset=utf-8");
				}
				String cr = line == null ? "" : (new String(line.getBytes("utf-8"), "utf-8"));
				bw.write(cr);
				bw.write("\n");
			}
		} catch (Exception e) {
			logger.debug("", e);
		} finally {
			if (fi != null) {
				fi = null;
			}
			if (fis != null) {
				try {
					fis.close();
					fis = null;
				} catch (Exception e) {
				}
			}
			if (br != null) {
				try {
					br.close();
					br = null;
				} catch (Exception e) {
				}
			}
			if (fos != null) {
				try {
					fos.close();
					fos = null;
				} catch (Exception e) {
				}
			}
			if (bw != null) {
				try {
					bw.flush();
					bw.close();
					bw = null;
				} catch (Exception e) {
				}
			}
		}

	}

	private String procEnc(String enc) {
		if (enc == null) {
			return "GB2312";
		}
		if (enc.toLowerCase().startsWith("utf")) {
			return enc;
		}
		if (enc.toLowerCase().startsWith("gb")) {
			return enc;
		}
		return "GB2312";
	}

	private boolean isHtmlFormat() {
		InputStream fis = null;
		BufferedReader br = null;
		String line;
		boolean ret = false;
		try {
			fis = new FileInputStream(xlsFile);
			String enc = procEnc((new EncodingDetectUtil()).getTxtFileEncoding(new File(xlsFile)));
			br = new BufferedReader(new InputStreamReader(fis, enc));
			while ((line = br.readLine()) != null && !ret) {
				if (line != null && line.toLowerCase().contains("html")) {
					ret = true;
					logger.debug("is html format");
				}
			}
		} catch (Exception e) {
			ret = false;
		} finally {
			if (fis != null) {
				try {
					fis.close();
					fis = null;
				} catch (Exception e) {
				}
			}
			if (br != null) {
				try {
					br.close();
					br = null;
				} catch (Exception e) {
				}
			}
		}
		return ret;
	}
}
