package fr.intech.s5.ws_hotel_android.utils;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Madione on 26/09/2017.
 */

public class RequestPackage implements Parcelable {
    private String endPoint;
    private String method = "GET";

    private Map<String, String> params = new HashMap<>();

    public String getEndpoint() {
        return endPoint;
    }
    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }
    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }
    public Map<String, String> getParams() {
        return params;
    }
    public void setParams(Map<String, String> params) {
        this.params = params;
    }
    public void setParam(String key, String value) {
        params.put(key, value);
    }

    public String getEncodedParams() {
        StringBuilder sb = new StringBuilder();

        for (String key : params.keySet()) {
            String value = null;

            try {
                value = URLEncoder.encode(params.get(key), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (sb.length() > 0)
                sb.append("&");

            sb.append(key).append("=").append(value);
        }

        return sb.toString();
    }

    public String getJSONParams() {
        JSONObject jsonObject = new JSONObject();

        for (String key : params.keySet()) {
            try {
                jsonObject.put(key, params.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return jsonObject.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.endPoint);
        dest.writeString(this.method);

        dest.writeInt(this.params.size());

        for (Map.Entry<String, String> entry : this.params.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
    }
    public RequestPackage() {
    }

    protected RequestPackage(Parcel in) {
        this.endPoint = in.readString();
        this.method = in.readString();

        int paramsSize = in.readInt();
        this.params = new HashMap<String, String>(paramsSize);

        for (int i = 0; i < paramsSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.params.put(key, value);
        }
    }

    public static final Creator<RequestPackage> CREATOR = new Creator<RequestPackage>() {
        @Override
        public RequestPackage createFromParcel(Parcel source) {
            return new RequestPackage(source);
        }

        @Override
        public RequestPackage[] newArray(int size) {
            return new RequestPackage[size];
        }
    };
}