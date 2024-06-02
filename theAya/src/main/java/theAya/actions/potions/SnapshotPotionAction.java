package theAya.actions.potions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theAya.actions.NewsBlitzAction;

public class SnapshotPotionAction extends AbstractGameAction {

    public SnapshotPotionAction() {
    }


    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new NewsBlitzAction(p));
        this.isDone = true;
    }
}
