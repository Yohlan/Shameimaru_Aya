package theAya.cards;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAya.AyaMod;
import theAya.characters.TheAya;


import java.util.ArrayList;

import static theAya.AyaMod.makeCardPath;

public class SpeedUp extends AbstractDynamicCard {

    public static final String ID = AyaMod.makeID(SpeedUp.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("SpeedUp.png");

    public static final String NAME = cardStrings.NAME;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAya.Enums.COLOR_GRAY;


    private static final int COST = 1;

    // /STAT DECLARATION/

    public SpeedUp() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        CloudstreamSprint cloudstreamSprint = new CloudstreamSprint();
        WingsOfAscend wingsofAscend = new WingsOfAscend();
        MultiCardPreview.add(this, new AbstractCard[] {cloudstreamSprint, wingsofAscend});
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CloudstreamSprint cloudstreamSprint = new CloudstreamSprint();
        WingsOfAscend wingsofAscend = new WingsOfAscend();
        if(this.upgraded){
            cloudstreamSprint.upgrade();
            wingsofAscend.upgrade();
        }
        ArrayList<AbstractCard> choices = new ArrayList<>();
        choices.add(cloudstreamSprint);
        choices.add(wingsofAscend);
        addToBot((AbstractGameAction)new ChooseOneAction(choices));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            MultiCardPreview.clear(this);
            CloudstreamSprint cloudstreamSprint = new CloudstreamSprint();
            WingsOfAscend wingsofAscend = new WingsOfAscend();
            cloudstreamSprint.upgrade();
            wingsofAscend.upgrade();
            MultiCardPreview.add(this, new AbstractCard[] {cloudstreamSprint, wingsofAscend});
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}