package com.hastatakip.hasta.hastaapp;



import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Map;

/**
 * Created by PC on 23.07.2015.
 */
public class WS
{
    public static String İstek(String webservis,String methodname,String soapaction,Map<String,Object> degerler)
    {
        String METHOD_NAME = methodname;
        String NAMESPACE = "http://tempuri.org/";
        String SOAP_ACTION = "http://tempuri.org/"+soapaction+"/"+methodname;
        String URL = "http://10.0.3.2/"+webservis;

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        for(Map.Entry<String, Object> pairs : degerler.entrySet())
        {
            request.addProperty(pairs.getKey(),pairs.getValue());
        }

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        androidHttpTransport.debug = true;

        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            String a= response.toString();
            return a;
        } catch (Exception e) {
            // TODO: handle exception
            e.getStackTrace();
            return "-1";
        }


    }
}
