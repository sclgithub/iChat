package com.techscl.ichat.utils;

import android.os.Handler;
import android.os.Message;

import com.techscl.ichat.base.Nearby;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 15/8/3.
 */
public class NearbyListSAX extends DefaultHandler {
    private List<Nearby> nearbyList;
    private Nearby nearby;
    private String preTag;
    private Handler handler;

    public NearbyListSAX(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        L.i("startDocument");
        nearbyList = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
//        L.i("startElement");
        if ("data".equals(localName)) {
            nearby = new Nearby();
        }
        preTag = localName;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
//        L.i("characters");
        String data = new String(ch, start, length);
        if ("deal_id".equals(preTag)) {
            nearby.setDeal_id(data);
        } else if ("deal_title".equals(preTag)) {
            nearby.setDeal_title(data);
        } else if ("deal_url".equals(preTag)) {
            nearby.setDeal_url(data);
        } else if ("deal_wap_url".equals(preTag)) {
            nearby.setDeal_wap_url(data);
        } else if ("deal_img".equals(preTag)) {
            nearby.setDeal_img(data);
        } else if ("value".equals(preTag)) {
            nearby.setValue(data);
        } else if ("price".equals(preTag)) {
            nearby.setPrice(data);
        } else if ("deal_subcate".equals(preTag)) {
            nearby.setDeal_subcate(data);
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        //L.i("endElement");
        if ("data".equals(localName)) {
            nearbyList.add(nearby);
            nearby = null;
        }
        preTag = null;
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        L.i("endDocument");
        Message message = handler.obtainMessage();
        message.what = 12;
        message.obj = nearbyList;
        handler.sendMessage(message);

    }


}
