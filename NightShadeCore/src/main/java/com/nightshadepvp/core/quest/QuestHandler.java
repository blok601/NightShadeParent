package com.nightshadepvp.core.quest;

import com.nightshadepvp.core.Core;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Blok on 12/10/2017.
 */
public class QuestHandler {

    private ArrayList<Quest> quests;

    public void setup(){
        this.quests = new ArrayList<>();

        addQuest(new HalfHeartQuest());
        addQuest(new BruteForceQuest());

        this.quests.sort(Comparator.comparing(Quest::getId));
    }

    private void addQuest(Quest quest) {
        this.quests.add(quest);
    }

    public Quest getQuest(String name){
        for (Quest quest : this.quests){
            if(quest.getName().equalsIgnoreCase(name)){
                return quest;
            }
        }

        return null;
    }

    public Quest getQuest(int id){
        for (Quest quest : this.quests){
            if(quest.getId() == id){
                return quest;
            }
        }

        return null;
    }

    public ArrayList<Quest> getQuests() {
        return quests;
    }
}
