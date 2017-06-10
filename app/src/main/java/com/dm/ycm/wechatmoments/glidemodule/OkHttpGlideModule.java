package com.dm.ycm.wechatmoments.glidemodule;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.dm.ycm.wechatmoments.MyApplication;
import com.dm.ycm.wechatmoments.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * A {@link GlideModule} implementation to replace Glide's default
 * {@link java.net.HttpURLConnection} based {@link com.bumptech.glide.load.model.ModelLoader} with an OkHttp based
 * {@link com.bumptech.glide.load.model.ModelLoader}.
 * <p/>
 * <p>
 * If you're using gradle, you can include this module simply by depending on the aar, the module will be merged
 * in by manifest merger. For other build systems or for more more information, see
 * {@link GlideModule}.
 * </p>
 */
public class OkHttpGlideModule implements GlideModule {

    // 默认存放图片的路径
    public final static String DEFAULT_SAVE_IMAGE_PATH = Environment.getExternalStorageDirectory()
            + File.separator + "CircleDemo"
            + File.separator + "Images"
            + File.separator;

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context,
                DEFAULT_SAVE_IMAGE_PATH, 50 * 1024 * 1024));
    }

//    @Override
//    public void applyOptions(Context context, GlideBuilder builder) {
//        // Do nothing.
//    }

    @Override
    public void registerComponents(Context context, Glide glide) {
//        SLSocketFactory sslSocketFactory =HttpsFactroy.getSSLSocketFactory_Certificate(context,"BKS", R.raw.XXX);
        OkHttpClient client = null;
        try {
            client = new OkHttpClient.Builder()
                    .sslSocketFactory(overlockCard().getSocketFactory())
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    }).build();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client));
    }

    /**
     * 忽略所有https证书
     */
    private SSLContext overlockCard() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
//        KeyStore tks = KeyStore.getInstance("BKS");
//        InputStream is = MyApplication.getContext().getResources().openRawResource(R.raw.ycm);
//        String PASSWD = "111111";
//        tks.load(is, PASSWD.toCharArray());
//        TrustManagerFactory trustManager = TrustManagerFactory.getInstance("X509");
//        trustManager.init(tks);
        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws
                    CertificateException {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws
                    CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
//                return null;
            }
        }};
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            return sslContext;
        } catch (Exception e) {
            Log.e(OkHttpGlideModule.class.getSimpleName(), "ssl出现异常");
            return null;
        }
    }
}
