package com.example.vinodhkumara.crazyeight;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity {
    private static String TAG = "MainActivity";
    private GridView mCardGridView = null;
    //private ListView mComputerCardList = null;
    private Context mContext = null;
    private ImageView mComputerCardList = null;
    private ImageView mTopCardImage = null;
    private ImageButton mFetchCardBtn = null;
    private TextView mPlayIstruction = null;
    private TextView mComputerPlayDetail = null;
    public Utils mUtils = null;

    public ArrayList<Integer> mImageThumbIds = new ArrayList<Integer>();
    public HashMap<String,Integer> cardsToImages = null;

    public ArrayList<String> mCardDeckTotal = new ArrayList<String>();
    public ArrayList<String> mCardDeck = new ArrayList<String>();
    public ArrayList<String> mHumanCards = new ArrayList<String>();
    public ArrayList<String> mComputerCards = new ArrayList<String>();
    public ArrayList<Integer> mHumanImageIds = new ArrayList<Integer>();
    public String mTopcard = null;
    public int mTopImageCardId = 0;
    private static int mCard_num = 5;
    public boolean isComputerPlay = false;
    public String mHumanSelectSuite = null;

    public HashMap<String,Integer> initDb(){
        HashMap<String,Integer> myList = new HashMap<String,Integer>();
        ArrayList<String> cards = mUtils.getTotalDeckAll();
        for (String card : cards) {
            try {
                Class[] classes = R.class.getDeclaredClasses();
                Class neededclass = null;
                for(Class c:classes){
                    if ("com.example.vinodhkumara.crazyeight.R$drawable".equals(c.getName())) {
                        Field[] fields = c.getFields();
                        neededclass = c;
                    }
                }
                Field myfield = neededclass.getField(card);
                int val = myfield.getInt(neededclass);
                //Log.d("TAG","Hash map Cards" + myfield.getName() + " : value is " + val);
                myList.put(card, val);
            }
            catch(Exception nsfe){
                nsfe.printStackTrace();
            }

        }
        return myList;
    }

    //private get
    //Field f = MainActivity.class.getDeclaredField("")

    /*public Integer[] mThumbIds = { R.drawable.ca, R.drawable.da, R.drawable.ha,
            R.drawable.cj, R.drawable.dj, R.drawable.hj, R.drawable.ck,
            R.drawable.dk, R.drawable.hk, R.drawable.sk, R.drawable.cq,
            R.drawable.dq, R.drawable.hq, R.drawable.sq, R.drawable.zc };*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Setting application only in portait mode
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        mUtils = new Utils();
        //myImages();
        cardsToImages = initDb();
        //cardsToImages.containsValue()
        initCards();
        initUI();

        mCardGridView.setAdapter(new CardDisplayGrid(mContext, mHumanImageIds));
        //mComputerCardList.setAdapter(new ComputerCardList(mContext));
        mCardGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                //mUtils.playHuman();
                String mStr = mUtils.getKeyByValue(cardsToImages, (int) id);
                int val = mHumanImageIds.get(position);
                /*Toast.makeText(mContext, "You Clicked " + position + "ImageID" + val + "ID " + id
                        + "Image String :" + mStr,
                        Toast.LENGTH_SHORT).show();*/
                playHumanUI(mStr, position);

                if (mUtils.getTotalNumberOfHumanCards() == 0) {
                    Toast.makeText(mContext, "You WON the Match ",
                            Toast.LENGTH_LONG).show();
                    finish();
                    return;
                }
                if (isComputerPlay) {
                    //Computer will play here
                    Log.d("TAG", "Computer will play ");
                    playComputerUI();
                }
                /*mUtils.playHuman(mStr);
                mHumanImageIds.remove(position);
                mCardGridView.setAdapter(new CardDisplayGrid(mContext, mHumanImageIds));*/
            }
        });

    }

    private final View.OnClickListener mFetchBtnOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.fetch_btn:
                    Toast.makeText(mContext, "You are Fetching cards ",
                            Toast.LENGTH_LONG).show();
                    //mImageThumbIds.add(mThumbIds[0]);
                    mUtils.fetchToHumanCards();
                    mHumanCards = mUtils.getHumanCard();
                    mHumanImageIds = mUtils.getHumanCardImageIds(mHumanCards, cardsToImages);
                    mCardGridView.setAdapter(new CardDisplayGrid(mContext, mHumanImageIds));
                    playComputerUI();
                    break;
                default:
                    break;
            }
        }
    };

    private void initUI() {
        mTopCardImage = (ImageView) findViewById(R.id.open_card_image);
        mFetchCardBtn = (ImageButton) findViewById(R.id.fetch_btn);
        mPlayIstruction = (TextView) findViewById(R.id.play_info);
        mComputerPlayDetail = (TextView) findViewById(R.id.computer_play_info);
        mCardGridView = (GridView) findViewById(R.id.card_grid);
        mComputerCardList = (ImageView) findViewById(R.id.computer_cards_list);

        mTopCardImage.setImageResource(mTopImageCardId);
        mFetchCardBtn.setImageResource(R.drawable.card_backside_1);
        mFetchCardBtn.setOnClickListener(mFetchBtnOnClickListener);
        mComputerCardList.setImageResource(R.drawable.computer_card_5);
    }

    private void initCards() {
        mCardDeck = mUtils.getDeck();
        mHumanCards = mUtils.getHumanCard();
        mHumanImageIds = mUtils.getHumanCardImageIds(mHumanCards, cardsToImages);
        mComputerCards = mUtils.getComputerCard();
        mTopcard = mUtils.getTopCard();
        //mTopImageCardId = mUtils.getCardImageId(mTopcard, cardsToImages);
        mTopImageCardId = cardsToImages.get(mTopcard);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void playHumanUI(String mStr, int position) {
        boolean isPlay = false;
        if (mStr.contains("8")) {
            // Show popup to select Faces
            mHumanImageIds.remove(position);
            mCardGridView.setAdapter(new CardDisplayGrid(mContext, mHumanImageIds));
            mUtils.playHumanEight(mStr);
            showPopUp();
            Log.d("TAG", "playHumanUI After Popup isComputerPlay =" + isComputerPlay );
            return;
        }
        isPlay = mUtils.playHuman(mStr);
        if (isPlay) {
            mHumanImageIds.remove(position);
            mCardGridView.setAdapter(new CardDisplayGrid(mContext, mHumanImageIds));
            mTopcard = mUtils.getTopCard();
            mTopImageCardId = cardsToImages.get(mTopcard);
            mTopCardImage.setImageResource(mTopImageCardId);
            isComputerPlay = true;
        } else {
            Toast.makeText(mContext, "IF YOU DONT HAVE CARD!! Please FETCH IT",
                    Toast.LENGTH_SHORT).show();
            isComputerPlay = false;
        }
    }

    public void playHumanEightUI(String mHumanSelectStr) {
        Log.d("TAG", "playHumanEightUI = " + mHumanSelectStr);
        mUtils.playUpdateTopcard(mHumanSelectStr);
        mTopcard = mUtils.getTopCard();
        mTopImageCardId = cardsToImages.get(mTopcard);
        mTopCardImage.setImageResource(mTopImageCardId);
        if (mUtils.getTotalNumberOfHumanCards() == 0) {
            Toast.makeText(mContext, "You WON the Match ",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        playComputerUI();
    }

    public void playComputerUI() {
        int num_of_computer_cards = 0;
        String mTopCard = mUtils.getTopCard();
        String mTopCardAfterComputerPlay = mUtils.playComputer(mTopCard);
        mTopImageCardId = cardsToImages.get(mTopCardAfterComputerPlay);
        mTopCardImage.setImageResource(mTopImageCardId);
        num_of_computer_cards = mUtils.getTotalNumberOfComputerCards();
        String mComputerDetail = "Your Computer has" + mUtils.getTotalNumberOfComputerCards() + "Cards" +  "And" + "Total card Deck  = " + mUtils.getTotalNumberOfCardDecks();
        mComputerPlayDetail.setText(mComputerDetail);
        // Displaying computer cards
        if (num_of_computer_cards <= 8) {
            displayComputerCards(num_of_computer_cards);
        }
        // Checking Computer WIN condition
        if (mUtils.getTotalNumberOfComputerCards() == 0) {
            Toast.makeText(mContext, "COMPUTER WON the Match ",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }
    }

    public String showPopUp() {
        Log.d("TAG", "showPopUp");
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.layout_popup);
        dialog.setTitle("Select Flower");
        dialog.setCancelable(false);
        ImageButton mImgBtnDiamond = (ImageButton) dialog.findViewById(R.id.dimond_face);
        ImageButton mImgBtnSpade = (ImageButton) dialog.findViewById(R.id.spade_face);
        ImageButton mImgBtnClover = (ImageButton) dialog.findViewById(R.id.clover_face);
        ImageButton mImgBtnHeart = (ImageButton) dialog.findViewById(R.id.heart_face);
        // if button is clicked, close the custom dialog
        mImgBtnDiamond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "You selected DIAMOND",
                        Toast.LENGTH_LONG).show();
                //playHumanEightUI("dz")
                mHumanSelectSuite = "dz";
                playHumanEightUI(mHumanSelectSuite);
                dialog.dismiss();
            }
        });
        mImgBtnSpade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "You selected SPADE",
                        Toast.LENGTH_LONG).show();
                mHumanSelectSuite = "sz";
                playHumanEightUI(mHumanSelectSuite);
                dialog.dismiss();
            }
        });
        mImgBtnClover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "You selected CLOVER",
                        Toast.LENGTH_LONG).show();
                mHumanSelectSuite = "cz";
                playHumanEightUI(mHumanSelectSuite);
                dialog.dismiss();
            }
        });
        mImgBtnHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "You selected HEART",
                        Toast.LENGTH_LONG).show();
                mHumanSelectSuite = "hz";
                playHumanEightUI(mHumanSelectSuite);
                dialog.dismiss();
            }
        });
        dialog.show();
        Log.d("TAG", "Dialog = " + mHumanSelectSuite);
        return mHumanSelectSuite;
    }

    public void displayComputerCards(int num_cards) {
        switch (num_cards) {
            case 1:
                mComputerCardList.setImageResource(R.drawable.computer_card);
                break;
            case 2:
                mComputerCardList.setImageResource(R.drawable.computer_card_2);
                break;
            case 3:
                mComputerCardList.setImageResource(R.drawable.computer_card_3);
                break;
            case 4:
                mComputerCardList.setImageResource(R.drawable.computer_card_4);
                break;
            case 5:
                mComputerCardList.setImageResource(R.drawable.computer_card_5);
                break;
            case 6:
                mComputerCardList.setImageResource(R.drawable.computer_card_6);
                break;
            case 7:
                mComputerCardList.setImageResource(R.drawable.computer_card_7);
                break;
            case 8:
                mComputerCardList.setImageResource(R.drawable.computer_card_8);
                break;
        }
    }
}
