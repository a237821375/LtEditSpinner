package cn.lognteng.editspinner.lteditspinner;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class LTEditSpinner<T> extends RelativeLayout
{

    public static interface OnESItemClickListener<Y>
    {
        public void onItemClick(Y item);
    }

    LinearLayout ltes_layout_root;
    EditText ltes_editText;
    ImageButton ltes_imagebutton_open_spinner;
    PopupWindow mPopupWindow ;
    ListView ltes_list;
    SpinnerAdapt<T> mAdapter;

    private List<T> mListAll_data = new ArrayList<T>();;
    private List<T> mList_data = new ArrayList<T>();;

    Context mContext;

    OnESItemClickListener mOnESItemClickListener = null;


    //初始化数据
    public LTEditSpinner initData(List<T> list_data, OnESItemClickListener onESItemClickListener)
    {
        if (list_data != null)
        {
            mListAll_data = list_data;
            mList_data = list_data;
        }
        else
        {
            mListAll_data.clear();
            mList_data.clear();
        }

        mOnESItemClickListener = onESItemClickListener;

        return this;
    }

    public LTEditSpinner setData(List<T> list_data )
    {
        if (list_data != null)
        {
            mList_data = list_data;
        }
        else
        {
            mList_data.clear();
        }

        if (mAdapter != null)
        {
            //更新数据
            mAdapter.setmList(mList_data);
            mAdapter.notifyDataSetChanged();
        }

        return this;
    }

    public LTEditSpinner(Context context) {
        super(context);
        initView(context);
    }

    public LTEditSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LTEditSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context)
    {
        mContext = context;

        View contentView = LayoutInflater.from(context).inflate(R.layout.ltes_layout_lt_edit_spinner, this, true);


        ltes_layout_root= (LinearLayout) findViewById(R.id.ltes_layout_root);
        ltes_editText= (EditText) findViewById(R.id.ltes_editText);
        ltes_imagebutton_open_spinner= (ImageButton) findViewById(R.id.ltes_imagebutton_open_spinner);

        ltes_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

                searchAndShowPop(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ltes_imagebutton_open_spinner.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                searchAndShowPop(ltes_editText.getText().toString());
            }
        });

        ltes_editText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                searchAndShowPop(ltes_editText.getText().toString());
            }
        });
    }

    public void searchAndShowPop(String str)
    {
        //搜索文字匹配新数据
        List<T> list = new ArrayList<>();
        for (int n=0; n<mListAll_data.size(); n++)
        {
            try
            {
                if (mListAll_data.get(n).toString().indexOf(str)>-1)
                {
                    list.add(mListAll_data.get(n));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }

        //关掉老的
        if (mPopupWindow !=null)
        {
            mPopupWindow.dismiss();
        }

        mPopupWindow = initPopupWindow(mContext, list.size());
        setData(list);

        if (list.size() == 0)
        {
            mPopupWindow.dismiss();
        }
        else
        {
            showDown();
        }
    }

    public LinearLayout getLtes_layout_root() {
        return ltes_layout_root;
    }

    public EditText getLtes_editText() {
        return ltes_editText;
    }

    public ImageButton getLtes_imagebutton_open_spinner() {
        return ltes_imagebutton_open_spinner;
    }

    public LTEditSpinner setImageBttonBackground(Drawable drawable)
    {
        ltes_imagebutton_open_spinner.setBackground(drawable);
        return this;
    }

    public LTEditSpinner setImageBttonBackground(int resid)
    {
        ltes_imagebutton_open_spinner.setBackgroundResource(resid);
        return this;
    }

    public LTEditSpinner setEditHint(String str)
    {
        ltes_editText.setHint(str);
        return this;
    }

    private PopupWindow initPopupWindow(Context context, int size)
    {

        View layoutOfList = LayoutInflater.from(context).inflate(R.layout.ltes_layout_spinner_list, null);

        //初始化列表数据
        ltes_list = (ListView)layoutOfList.findViewById(R.id.ltes_list);
        mAdapter = new SpinnerAdapt<T>( mList_data, context);
        ltes_list.setAdapter(mAdapter);
        //点击某一条
        ltes_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                final T item = mList_data.get(i);
                ltes_editText.setText(mList_data.get(i).toString());
                ltes_editText.setSelection(ltes_editText.getText().length());//光标放到最后
                mPopupWindow.dismiss();

                if (mOnESItemClickListener!=null)
                {
                    mOnESItemClickListener.onItemClick(item);
                }
            }
        });

        PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setWidth(ltes_editText.getMeasuredWidth());
        if (size>4)
        {
            popupWindow.setHeight(400);
        }
        else
        {
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        }


        popupWindow.setContentView(layoutOfList);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(false);

        return popupWindow;
    }

    private void showTop()
    {
        int[] location = new int[2];
        ltes_editText.getLocationOnScreen(location);
        mPopupWindow.showAtLocation(ltes_editText, Gravity.NO_GRAVITY, location[0], location[1]-mPopupWindow.getHeight());
    }

    private void showDown()
    {
        mPopupWindow.showAsDropDown(ltes_editText);
    }

    private void showLeft()
    {
        int[] location = new int[2];
        ltes_editText.getLocationOnScreen(location);
        mPopupWindow.showAtLocation(ltes_editText, Gravity.NO_GRAVITY, location[0] - mPopupWindow.getWidth(), location[1]);
    }

    private void showRight()
    {
        int[] location = new int[2];
        ltes_editText.getLocationOnScreen(location);
        mPopupWindow.showAtLocation(ltes_editText, Gravity.NO_GRAVITY, location[0] + mPopupWindow.getWidth(), location[1]);
    }

    public String getValue()
    {
        return ltes_editText.getText().toString();
    }
}
