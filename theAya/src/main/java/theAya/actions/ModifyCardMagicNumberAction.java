//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package theAya.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import theAya.cards.AbstractDefaultCard;

import java.util.Iterator;
import java.util.UUID;

public class ModifyCardMagicNumberAction extends AbstractGameAction {
    private UUID uuid;

    public ModifyCardMagicNumberAction(UUID targetUUID, int amount) {
        this.setValues(this.target, this.source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.uuid = targetUUID;
    }

    public void update() {
        Iterator var1 = GetAllInBattleInstances.get(this.uuid).iterator();

        while(var1.hasNext()) {
            AbstractDefaultCard c = (AbstractDefaultCard)var1.next();
            c.baseMagicNumber += this.amount;
        }

        this.isDone = true;
    }
}
