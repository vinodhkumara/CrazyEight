package com.example.vinodhkumara.crazyeight;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by vinodh.kumara on 8/13/2015.
 */
public class Utils {
    public static String TAG = "Utils";
    public ArrayList<String> mCardDeckAll = new ArrayList<String>();
    public ArrayList<String> mCardDeckTotal = new ArrayList<String>();
    //public ArrayList<String> mTotalCards = new ArrayList<String>();
    public ArrayList<String> mCardDeck = new ArrayList<String>();
    public String[] mCardSuites = {"c","s","d","h"};
    public String[] mCardFaces = {"a","2","3","4","5","6","7","8","9","x","j","q","k","z"};
    public ArrayList<String> mHumanCards = new ArrayList<String>();
    public ArrayList<String> mComputerCards = new ArrayList<String>();
    public String mTopcard = null;
    public ArrayList<Integer> mPlayerCardIds = new ArrayList<Integer>();
    private static int mCard_num = 5;

    public Utils() {
        mCardDeckAll = build_cards_All(mCardSuites, mCardFaces);
        mCardDeckTotal = build_cards(mCardSuites,mCardFaces);
        //mTotalCards = mCardDeckTotal;
        mCardDeck = shuffle_cards(mCardDeckTotal);
        //getCardDeck(mCardDeck);
        mHumanCards = getCards(mCardDeck, mCard_num);
        //getCardDeck(mCardDeck);
        mComputerCards = getCards(mCardDeck, mCard_num);
        mTopcard = getInitTopCard(mCardDeck);
    }

    public ArrayList<String> build_cards_All(String[] mSuites, String[] mFaces) {
        ArrayList<String> mCards = new ArrayList<String>();
        for (String mFace: mFaces) {
            for (String mSuite: mSuites) {
                mCards.add(mSuite + mFace);
                Log.d("Vinodh","build_cards_All S&F:" +mSuite + mFace );
            }
        }
        for(String str: mCards){
            Log.d("Vinodh","build_cards_All :" + str);
        }
        return mCards;
    }

    public ArrayList<String> build_cards(String[] mSuites, String[] mFaces) {
        ArrayList<String> mCards = new ArrayList<String>();
        for (String mFace: mFaces) {
            if (mFace.contains("z")) {
                Log.d("Vinodh","Build Cards ingore face Z" + mFace );
                continue;
            }
            for (String mSuite: mSuites) {
                mCards.add(mSuite + mFace);
                Log.d("Vinodh","Build Cards S&F:" +mSuite + mFace );
            }
        }
        for(String str: mCards){
            Log.d("Vinodh","Build Cards :" + str);
        }
        return mCards;
    }

    public ArrayList<String> shuffle_cards(ArrayList<String> mCards) {
        ArrayList<String> mShufflesCards = new ArrayList<String>();
        mShufflesCards.addAll(mCards);
        //mShufflesCards = mCards;
        for(String str: mShufflesCards){
            Log.d("TAG", "Before ShuffleCards :" + str);
        }
        Collections.shuffle(mShufflesCards);
        for(String str: mShufflesCards){
            Log.d("TAG", "After shuffle Cards :" + str);
        }
        return mShufflesCards;
    }

    public ArrayList<String> getCards(ArrayList<String> mCards, int num) {
        ArrayList<String> mGetCards = new ArrayList<String>();
        for (int i = 0; i < num; i++) {
            mGetCards.add(mCards.remove(0));
        }
        for(String str: mGetCards){
            Log.d("TAG","Get Cards :" + str);
        }

        return mGetCards;
    }

    public String getInitTopCard(ArrayList<String> mCards) {
        Log.d("TAG", "Get Top Card :" + mCards.get(0));
        return mCards.get(0);
    }

    public ArrayList<Integer> getHumanCardImageIds(ArrayList<String> mCards,HashMap<String,Integer> mCardHash) {
        ArrayList<Integer> mGetCards = new ArrayList<Integer>();
        for(String mystr: mCards){
            int i = 0;
            //Log.d("TAG","MISSION Card: " + mystr);
            //Log.d("TAG","getHumanCard:" +  mCardHash.get(mystr));
            for(String val:mCardHash.keySet()){
                //Log.d("TAG","get new keys " + "" +  val + " ids " + mCardHash.get(val));
            }
            mGetCards.add(mCardHash.get(mystr));
            //Log.d("TAG","ID: " + "" + mGetCards.get(i));
            i++;
        }
        return mGetCards;
    }

    public int getCardImageId (String mCard, HashMap<String,Integer> mCardHash) {
        int id = mCardHash.get(mCard);
        return id;
    }

    /*public String getCardString (int mCardId, HashMap<String,Integer> mCardHash) {

        Set set = mCardHash.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            String mStr = null;
            Map.Entry mentry = (Map.Entry)iterator.next();
            if (mentry.getValue() == mCardId) {
                mCardstr = mentry.getKey();
            }
        }
        return mCardstr;
    }*/

    public <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public boolean playHuman(String mCard) {
        boolean mResult = false;
        Log.d("TAG","playHuman" + mCard);
        String mStr = mCard;
        Log.d("TAG","playHuman mStr = " + mCard);
        Log.d("TAG","playHuman mTopcard = " + mTopcard);
        boolean isContainChar = false;

        for(int i = 0; i < mStr.length(); i++) {
            if(mStr.charAt(i) == mTopcard.charAt(i)) {
                isContainChar = true;
                break;
            }
        }
        if (isContainChar) {
            removeHumanCards(mStr);
            mResult = true;
        }
        Log.d("TAG", "playHuman mResult =" + mResult);
        return mResult;
    }

    public void playHumanEight(String mCard) {
        Log.d("TAG","playHumanEight" + mCard);
        String mStr = mCard;
        Log.d("TAG","playHumanEight mStr = " + mCard);
        Log.d("TAG","playHumanEight mTopcard = " + mTopcard);
        removeHumanCards(mStr);
    }

    public void playUpdateTopcard(String mCard) {
        mTopcard = mCard;
    }

    public void fetchToHumanCards() {
        Log.d("TAG","fetchToHumanCards" + mCardDeck.get(0));
        mHumanCards.add(mCardDeck.get(0));
        mCardDeck.remove(0);

    }

    public void removeHumanCards(String mCard) {
        Log.d("TAG","update_Human_Cards" + mCard);
        int i = 0;
        for(String str: mHumanCards){
            Log.d("TAG", "update_Human_Cards index:" + mHumanCards.get(i) + "i=" + i);
            if (mCard.equals(str)) {
                Log.d("TAG","update_Human_Cards equals:" + mHumanCards.get(i));
                mTopcard = mHumanCards.get(i);
                mHumanCards.remove(i);
                mCardDeck.add(mCard);
                break;
            }
            i++;
        }
    }

    public String playComputer(String mCard) {
        Log.d("TAG","playComputer" + mCard);
        boolean isContainChar = false;
        boolean isEight = false;
        String mComCard = null;
        for (String mStr : mComputerCards) {
            if (mStr.contains("8")) {
                isEight = true;
                continue;
            }
            //Checking same face and same suite
            for (int i = 0; i < mStr.length(); i++) {
                if (mStr.charAt(i) == mCard.charAt(i)) {
                    isContainChar = true;
                    isEight = false;
                    mComCard = mStr;
                    break;
                }
            }
        }
        // Removing the human cards, If same face card or same suite card present
        if (isContainChar) {
            Log.d("TAG","playComputer computercard = " + mComCard);
            removeComputerCards(mComCard,isEight);
        } else {
            Log.d("TAG", "No Same Suite and No same Face cards");
            if (isEight) {
                for (String mStr : mComputerCards) {
                    if (mStr.contains("8")) {
                        mComCard = mStr;
                        break;
                    }
                }
                Log.d("TAG", "isEight = " + mComCard);
                removeComputerCards(mComCard,isEight);
            } else {
                Log.d("TAG", "playComputer Going to Fetch card");
                fetchToComputerCards();
            }
        }
        Log.d("TAG","playComputer mTopcard = " + mTopcard);
        return mTopcard;
    }

    /*public void removeComputerCards(String mCard) {
        Log.d("TAG","removeComputerCards" + mCard);
        int i = 0;
        for(String str: mComputerCards){
            Log.d("TAG","update_Computer_Cards index:" + mComputerCards.get(i) + "i=" + i);
            if (mCard.equals(str)) {
                Log.d("TAG","update_Computer_Cards equals:" + mComputerCards.get(i));
                mTopcard = mComputerCards.get(i);
                mComputerCards.remove(i);
                mCardDeck.add(mCard);
                break;
            }
            i++;
        }
    }*/

    public void removeComputerCards(String mCard, boolean isEight) {
        if (isEight) {
            int i = 0;
            for(String mStr: mComputerCards) {
                Log.d("TAG","update_Computer_Cards index isEight: " + mComputerCards.get(i) + "i=" + i);
                if (mStr.contains("8")) {
                    String mSuite = null;
                    Log.d("TAG","update_Computer_Cards equals isEight:" + mComputerCards.get(i));
                    mSuite = findMaxSuite(mComputerCards) + "z";
                    mTopcard = mSuite;
                    mComputerCards.remove(i);
                    mCardDeck.add(mCard);
                    break;
                }
                i++;
            }
        } else {
            int i = 0;
            for(String str: mComputerCards){
                Log.d("TAG","update_Computer_Cards index:" + mComputerCards.get(i) + "i=" + i);
                if (mCard.equals(str)) {
                    Log.d("TAG","update_Computer_Cards equals:" + mComputerCards.get(i));
                    mTopcard = mComputerCards.get(i);
                    mComputerCards.remove(i);
                    mCardDeck.add(mCard);
                    break;
                }
                i++;
            }

        }

    }

    public void fetchToComputerCards() {
        Log.d("TAG","fetchToComputerCards" + mCardDeck.get(0));
        mComputerCards.add(mCardDeck.get(0));
        mCardDeck.remove(0);
    }

    public ArrayList<String> getTotalDeckAll(){
        int i = 0;
        for(String str: mCardDeckAll) {
            Log.d("TAG","mCardDeckAll: " + mCardDeckAll.get(i));
            i++;
        }
        return mCardDeckAll;
    }

    public ArrayList<String> getTotalDeck(){
        int i = 0;
        for(String str: mCardDeckTotal) {
            Log.d("TAG","mCardDeckTotal: " + mCardDeckTotal.get(i));
            i++;
        }
        return mCardDeckTotal;
    }

    public ArrayList<String> getDeck(){
        for(String str: mCardDeck){
            int i = 0;
            Log.d("TAG","Card:" + mCardDeck.get(i));
            i++;
        }
        return mCardDeck;
    }

    public ArrayList<String> getHumanCard() {
        Log.d("TAG","getHumanCard" + "Inside GetHumanCard");
        int i = 0;
        for(String str: mHumanCards){
            Log.d("TAG","getHumanCard:" + mHumanCards.get(i));
            i++;
        }
        return mHumanCards;
    }

    public ArrayList<String> getComputerCard() {
        Log.d("Vinodh getComputerCard ", "Inside getComputerCard");
        return mComputerCards;
    }

    public String getTopCard() {
        Log.d("TAG","getmTopcard " + mTopcard);
        return mTopcard;
    }

    public ArrayList<String> getCardDeck(ArrayList<String> mCards) {
        ArrayList<String> mGetCards = new ArrayList<String>();
        int i = 0;
        for(String str: mCards){
            Log.d("TAG","Get Card Deck : " + "Num:" + i +"  " + str);
            i++;
        }
        return mGetCards;
    }

    public int getTotalNumberOfComputerCards () {
        int mNum = mComputerCards.size();
        Log.d("TAG","getTotalNumberOfComputerCards " + mNum);
        return mNum;
    }

    public int getTotalNumberOfHumanCards () {
        int mNum = mHumanCards.size();
        Log.d("TAG","getTotalNumberOfHumanCards " + mNum);
        return mNum;
    }

    public int getTotalNumberOfCardDecks () {
        int mNum = mCardDeck.size();
        Log.d("TAG","getTotalNumberOfCardDecks " + mNum);
        return mNum;
    }

    public String findMaxSuite (ArrayList<String> mCards) {
        Log.d("TAG","findMaxSuite ");
        int D = 0, H = 0, S = 0, C = 0;
        ArrayList<Integer> mSuites = new ArrayList<>();
        mSuites.add(D);
        mSuites.add(H);
        mSuites.add(S);
        mSuites.add(C);
        String mSuite = null;
        for(String mStr: mCards){
            if (mStr.contains ("d")) {
                D += 1;
            } else if (mStr.contains("h")) {
                H += 1;
            } else if (mStr.contains("s")) {
                S += 1;
            } else {
                C += 1;
            }
        }
        Log.d("TAG","findMaxSuite D = " + D + "H = " + H + "S = " + S + "C =" + C);
        mSuites.add(D);
        mSuites.add(H);
        mSuites.add(S);
        mSuites.add(C);
        int max_val = Collections.max(mSuites);

        if (max_val == D) {
            mSuite = "d";
        } else if (max_val == H) {
            mSuite = "h";
        } else if (max_val == S) {
            mSuite = "s";
        } else if (max_val == C) {
            mSuite = "c";
        }
        return mSuite;
    }
}
