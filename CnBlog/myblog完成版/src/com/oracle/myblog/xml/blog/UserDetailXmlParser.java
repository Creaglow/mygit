package com.oracle.myblog.xml.blog;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import org.xml.sax.Attributes;

import com.oracle.myblog.entity.Users;

/**
 * Users����xml������
 * 
 * @author walkingp
 * 
 */
public class UserDetailXmlParser extends DefaultHandler {
	final String ENTRY_TAG = "feed";// �����
	final String ENTRY_ID_TAG = "id";// ��ű��
	final String ENTRY_AUTHOR_NAME_TAG = "name";// �û�����
	final String ENTRY_AVATOR_TAG = "logo";// ͷ���ַ
	final String ENTRY_URL_TAG = "uri";// ʵ����ַ��ǩ
	final String ENTRY_POST_COUNT_TAG = "postcount";// ������
	final String ENTRY_END_TAG = "entry";// ��ֹ���

	private Users entity;// ��������
	private boolean isStartParse;// ��ʼ����
	private StringBuilder currentDataBuilder;// ��ǰȡ����ֵ

	/**
	 * Ĭ�Ϲ��캯��
	 */
	public UserDetailXmlParser() {
		super();
	}

	/**
	 * ���캯��
	 * 
	 * @return
	 */
	public UserDetailXmlParser(Users list) {
		this.entity = list;
	}

	/**
	 * ������
	 * 
	 * @return
	 */
	public Users GetUserDetail() {
		return entity;
	}

	/**
	 * �ĵ���ʼʱ����
	 */
	public void startDocument() throws SAXException {
		Log.i("Users", "�ĵ�������ʼ");
		super.startDocument();
		currentDataBuilder = new StringBuilder();
	}

	/**
	 * ��ȡ������XML���
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if (localName.equalsIgnoreCase(ENTRY_TAG)) {
			entity = new Users();
			isStartParse = true;
		}
	}

	/**
	 * ��ȡԪ������
	 * 
	 * @param ch
	 * @param start
	 * @param length
	 * @throws SAXException
	 */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		currentDataBuilder.append(ch, start, length);
	}

	/**
	 * Ԫ�ؽ���ʱ����
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		if (isStartParse) {// ����Ŀ��
			String chars = currentDataBuilder.toString();
			Log.i("Users", "���ڽ���" + localName);
			// ����
			if (localName.equalsIgnoreCase(ENTRY_AUTHOR_NAME_TAG)) {// �û���
				entity.SetUserName(chars);
			} else if (localName.equalsIgnoreCase(ENTRY_AVATOR_TAG)) {// �û�ͷ��
				entity.SetAvator(chars);
			} else if (localName.equalsIgnoreCase(ENTRY_URL_TAG)) {// ���͵�ַ
				entity.SetBlogUrl(chars);
			} else if (localName.equalsIgnoreCase(ENTRY_POST_COUNT_TAG)) {// ��������
				int postCount = Integer.parseInt(chars);
				entity.SetBlogCount(postCount);
			} else if (localName.equalsIgnoreCase(ENTRY_END_TAG)) {// ��ֹ
				isStartParse = false;
			}
		}

		currentDataBuilder.setLength(0);
	}

	/**
	 * �ĵ�����ʱ����
	 */
	public void endDocument() throws SAXException {
		Log.i("Users", "�ĵ���������");
		super.endDocument();
	}
}
