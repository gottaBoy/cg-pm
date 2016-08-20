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
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

/**
 * word to html
 * 
 * @author Sergey Vladimirov (vlsergey {at} gmail {dot} com)
 */
public class WordToHtml {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private String docFile;
	private String htmlFile;

	public WordToHtml(String docFile, String htmlFile) {
		this.docFile = docFile;
		this.htmlFile = htmlFile;
	}

	public void printPage() throws Exception {
		String ht = getHtmlText(docFile);
		FileWriter fw = null;
		try {
			fw = new FileWriter(htmlFile);
			fw.write(ht);// 无法实现追加可写，存在覆盖现象
		} catch (IOException e1) {
			throw e1;
		} finally {
			try {
				if (fw != null) {
					fw.flush();
					fw.close();
					fw = null;
				}
			} catch (Exception e) {
				logger.debug("", e);
			}
		}
	}

	private String getHtmlText(final String sampleFileName) throws Exception {
		return getHtmlText(sampleFileName, false);
	}

	private String getHtmlText(final String sampleFileName, boolean emulatePictureStorage) throws Exception {
		FileInputStream fi = null;
		String result = null;
		try {
			fi = new FileInputStream(sampleFileName);
			HWPFDocument hwpfDocument = new HWPFDocument(fi);

			Document newDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(newDocument);

			if (emulatePictureStorage) {
				wordToHtmlConverter.setPicturesManager(new PicturesManager() {
					public String savePicture(byte[] content, PictureType pictureType, String suggestedName,
							float widthInches, float heightInches) {
						return suggestedName;
					}
				});
			}

			wordToHtmlConverter.processDocument(hwpfDocument);

			StringWriter stringWriter = new StringWriter();

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			transformer.setOutputProperty(OutputKeys.METHOD, "html");
			transformer.transform(new DOMSource(wordToHtmlConverter.getDocument()), new StreamResult(stringWriter));

			result = stringWriter.toString();

		} catch (Exception e) {

		} finally {
			try {
				if (fi != null) {
					fi.close();
					fi = null;
				}
			} catch (Exception e) {
				logger.debug("", e);
			}
		}
		return result;
	}
}
