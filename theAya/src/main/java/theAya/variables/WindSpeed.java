package theAya.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theAya.characters.TheAya;

import static theAya.AyaMod.makeID;

public class WindSpeed extends DynamicVariable
{   // Custom Dynamic Variables are what you do if you need your card text to display a cool, changing number that the base game doesn't provide.
    // If the !D! and !B! (for Damage and Block) etc. are not enough for you, this is how you make your own one. It Changes In Real Time!
  
    
    // This is what you type in your card string to make the variable show up. Remember to encase it in "!"'s in the json!
    @Override
    public String key()
    {
        return makeID("WindSpeed");
    }

    // Checks whether the current value is different than the base one. 
    // For example, this will check whether your damage is modified (i.e. by strength) and color the variable appropriately (Green/Red).
    @Override
    public boolean isModified(AbstractCard card)
    {
        return card.isDamageModified;
    }
   
    // The value the variable should display.
    @Override
    public int value(AbstractCard card)
    {
        return TheAya.getWindSpeed();
    }
    
    // The baseValue the variable should display.
    @Override
    public int baseValue(AbstractCard card)
    {   
        return 0;
    }

    @Override
    public boolean upgraded(AbstractCard card)
    {               
       return false;
    }
}