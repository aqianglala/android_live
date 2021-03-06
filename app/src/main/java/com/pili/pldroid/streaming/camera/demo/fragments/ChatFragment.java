package com.pili.pldroid.streaming.camera.demo.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.pili.pldroid.streaming.camera.demo.R;
import com.pili.pldroid.streaming.camera.demo.viewmodels.ChatFragmentVM;

import org.kymjs.chat.ChatActivity;
import org.kymjs.chat.OnOperationListener;
import org.kymjs.chat.adapter.ChatAdapter;
import org.kymjs.chat.bean.Emojicon;
import org.kymjs.chat.bean.Faceicon;
import org.kymjs.chat.bean.Message;
import org.kymjs.chat.emoji.DisplayRules;
import org.kymjs.chat.widget.KJChatKeyboard;
import org.kymjs.kjframe.ui.KJFragment;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2016/3/18.
 */
public class ChatFragment extends KJFragment {

    public static final int REQUEST_CODE_GETIMAGE_BYSDCARD = 0x1;

    private KJChatKeyboard box;
    private ListView mRealListView;

    List<Message> datas = new ArrayList<Message>();
    private ChatAdapter adapter;
    private ChatFragmentVM mViewmodel;

    private Handler handler = new Handler();

    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View rootView = layoutInflater.inflate(R.layout.activity_chat, viewGroup, false);
        mViewmodel = new ChatFragmentVM(this);
        return rootView;
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        box = (KJChatKeyboard) parentView.findViewById(org.kymjs.chat.R.id.chat_msg_input_box);
        mRealListView = (ListView) parentView.findViewById(org.kymjs.chat.R.id.chat_listview);

        mRealListView.setSelector(android.R.color.transparent);
        initMessageInputToolBox();
        initListView();
    }

    private void initMessageInputToolBox() {
        box.setOnOperationListener(new OnOperationListener() {
            @Override
            public void send(String content) {
                // 发送到服务端
                if(mViewmodel.sendMessage(content)){// 发送成功
                    Toast.makeText(getActivity(),"发送成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"发送失败",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void selectedFace(Faceicon content) {
                Message message = new Message(Message.MSG_TYPE_FACE, Message.MSG_STATE_SUCCESS,
                        "Tom", "avatar", "Jerry", "avatar", content.getPath(), true, true, new
                        Date());
                datas.add(message);
                adapter.refresh(datas);
//                createReplayMsg(message);
            }

            @Override
            public void selectedEmoji(Emojicon emoji) {
                box.getEditTextBox().append(emoji.getValue());
            }

            @Override
            public void selectedBackSpace(Emojicon back) {
                DisplayRules.backspace(box.getEditTextBox());
            }

            @Override
            public void selectedFunction(int index) {
                switch (index) {
                    case 0:
                        goToAlbum();
                        break;
                    case 1:
                        ViewInject.toast("跳转相机");
                        break;
                }
            }
        });

        List<String> faceCagegory = new ArrayList<>();
//        File faceList = FileUtils.getSaveFolder("chat");
        File faceList = new File("");
        Log.e("getAbsolutePath",faceList.getAbsolutePath());
        if (faceList.isDirectory()) {
            File[] faceFolderArray = faceList.listFiles();
            for (File folder : faceFolderArray) {
                if (!folder.isHidden()) {
                    faceCagegory.add(folder.getAbsolutePath());
                    Log.e("getAbsolutePath",folder.getAbsolutePath());
                }
            }
        }

        box.setFaceData(faceCagegory);
        mRealListView.setOnTouchListener(getOnTouchListener());
    }

    public void refreshAdapter(final Message message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                datas.add(message);
                adapter.refresh(datas);
                mRealListView.setSelection(datas.size());
            }
        });
    }

    private void initListView() {
//        byte[] emoji = new byte[]{
//                (byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x81
//        };
//        Message message = new Message(Message.MSG_TYPE_TEXT,
//                Message.MSG_STATE_SUCCESS, "\ue415", "avatar", "Jerry", "avatar",
//                new String(emoji), false, true, new Date(System.currentTimeMillis()
//                - (1000 * 60 * 60 * 24) * 8));
//        MessageBean message1 = new MessageBean(MessageBean.MSG_TYPE_TEXT,
//                MessageBean.MSG_STATE_SUCCESS, "Tom", "avatar", "Jerry", "avatar",
//                "以后的版本支持链接高亮喔:http://www.kymjs.com支持http、https、svn、ftp开头的链接",
//                true, true, new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24) * 8));
//        MessageBean message2 = new MessageBean(MessageBean.MSG_TYPE_PHOTO,
//                MessageBean.MSG_STATE_SUCCESS, "Tom", "avatar", "Jerry", "avatar",
//                "http://static.oschina.net/uploads/space/2015/0611/103706_rpPc_1157342.png",
//                false, true, new Date(
//                System.currentTimeMillis() - (1000 * 60 * 60 * 24) * 7));
//        MessageBean message6 = new MessageBean(MessageBean.MSG_TYPE_TEXT,
//                MessageBean.MSG_STATE_FAIL, "Tom", "avatar", "Jerry", "avatar",
//                "test send fail", true, false, new Date(
//                System.currentTimeMillis() - (1000 * 60 * 60 * 24) * 6));
//        MessageBean message7 = new MessageBean(MessageBean.MSG_TYPE_TEXT,
//                MessageBean.MSG_STATE_SENDING, "Tom", "avatar", "Jerry", "avatar",
//                "<a href=\"http://kymjs.com\">自定义链接</a>也是支持的", true, true, new Date(System.currentTimeMillis()
//                - (1000 * 60 * 60 * 24) * 6));

//        datas.add(message);
//        datas.add(message1);
//        datas.add(message2);
//        datas.add(message6);
//        datas.add(message7);

        adapter = new ChatAdapter(getActivity(), datas, getOnChatItemClickListener());
        mRealListView.setAdapter(adapter);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && box.isShow()) {
//            box.hideLayout();
//            return true;
//        } else {
//            return super.onKeyDown(keyCode, event);
//        }
//    }



    /**
     * 跳转到选择相册界面
     */
    private void goToAlbum() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "选择图片"),
                    REQUEST_CODE_GETIMAGE_BYSDCARD);
        } else {
            intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "选择图片"),
                    REQUEST_CODE_GETIMAGE_BYSDCARD);
        }

    }

    /**
     * 若软键盘或表情键盘弹起，点击上端空白处应该隐藏输入法键盘
     *
     * @return 会隐藏输入法键盘的触摸事件监听器
     */
    private View.OnTouchListener getOnTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                box.hideLayout();
                box.hideKeyboard(getActivity());
                return false;
            }
        };
    }

    /**
     * @return 聊天列表内容点击事件监听器
     */
    private OnChatItemClickListener getOnChatItemClickListener() {
        return new OnChatItemClickListener() {
            @Override
            public void onPhotoClick(int position) {
                KJLoger.debug(datas.get(position).getContent() + "点击图片的");
                ViewInject.toast(getActivity(), datas.get(position).getContent() + "点击图片的");
            }

            @Override
            public void onTextClick(int position) {
            }

            @Override
            public void onFaceClick(int position) {
            }
        };
    }

    /**
     * 聊天列表中对内容的点击事件监听
     */
    public interface OnChatItemClickListener extends ChatActivity.OnChatItemClickListener {
        void onPhotoClick(int position);

        void onTextClick(int position);

        void onFaceClick(int position);
    }

    private int roomId;
    public void setRoomId(int roomId){
        this.roomId = roomId;
        mViewmodel.connect(roomId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewmodel.closeSocket();
    }
}
