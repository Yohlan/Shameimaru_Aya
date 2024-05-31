package theAya.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import theAya.AyaMod;
import theAya.characters.TheAya;
import theAya.powers.FlightPower;
import theAya.powers.TremblePower;

import static theAya.AyaMod.makeCardPath;

public class StartlingTeleport extends AbstractDynamicCard {



    public static final String ID = AyaMod.makeID(StartlingTeleport.class.getSimpleName());
    public static final String IMG = makeCardPath("StartlingTeleport.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAya.Enums.COLOR_GRAY;

    private static final int COST = 1;

    // /STAT DECLARATION/


    public StartlingTeleport() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 2;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            addToBot(new ReducePowerAction(p, p, FlightPower.POWER_ID, p.getPower(FlightPower.POWER_ID).amount));
            if(upgraded || TheAya.getWindSpeed() < 10){
                addToBot(new ApplyPowerAction(m, p, new TremblePower(m, p, this.magicNumber), this.magicNumber));
            }
            else{
                TheAya.loseWindSpeed(10);
                addToBot(new ApplyPowerAction(m, p, new TremblePower(m, p, 3), 3));
            }
    }

    //Upgraded stats.
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            rawDescription = UPGRADE_DESCRIPTION;
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return AbstractDungeon.player.hasPower(FlightPower.POWER_ID);
    }
    private final Color dGlowColor = this.glowColor;
    @Override
    public void triggerOnGlowCheck() {
        if(upgraded || TheAya.getWindSpeed() < 10){
            this.glowColor = dGlowColor;
        }else{
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }
}