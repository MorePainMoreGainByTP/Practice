package com.example.swjtu.testwords2voice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

public class MainActivity extends AppCompatActivity {
    private static final String MYID = "58b2ad9a";

    private EditText editText;
    private SpeechSynthesizer synthesizer;  //语音合成对象
    private SynthesizerListener mSynListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
            //缓冲进度回调
            //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在 文本中结束位置，info为附加信息
        }

        @Override
        public void onSpeakPaused() {
        }

        @Override
        public void onSpeakResumed() {
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            //播放进度回调
            //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        }

        @Override
        public void onCompleted(SpeechError speechError) {
            //会话结束回调接口，没有错误时，error为null
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {
            //会话事件回调接口
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        //初始化即创建语音配置对象，只有初始化后才可以使用 MSC 的各项服务
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=" + MYID);
        //1.创建 SpeechSynthesizer 对象, 第二个参数：本地合成时传 InitListener
        synthesizer = SpeechSynthesizer.createSynthesizer(this, null);
        //2.合成参数设置
        synthesizer.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan"); //设置发音人
        synthesizer.setParameter(SpeechConstant.SPEED, "50");//设置语速
        synthesizer.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围 0~100
        synthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        //设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
        //仅支持保存为 pcm 和 wav 格式，如果不需要保存合成音频，注释该行代码
        //synthesizer.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");

    }

    private void initViews() {
        editText = (EditText) findViewById(R.id.edit_words);
    }

    //语音合成
    public void makeVoice(View v) {
        String words = editText.getText().toString();
        if (words != null && !words.equals("")) {
            //3.开始合成
            synthesizer.startSpeaking(words, mSynListener);
        } else {
            Toast.makeText(this, "没有要播放的文字", Toast.LENGTH_SHORT).show();
        }
    }
}
