/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.commander2015;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author emerald000
 */
public class AEthersnatch extends CardImpl {

    public AEthersnatch(UUID ownerId) {
        super(ownerId, 9, "AEthersnatch", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{4}{U}{U}");
        this.expansionSetCode = "C15";

        // Gain control of target spell. You may choose new targets for it.
        this.getSpellAbility().addEffect(new AEthersnatchEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public AEthersnatch(final AEthersnatch card) {
        super(card);
    }

    @Override
    public AEthersnatch copy() {
        return new AEthersnatch(this);
    }
}

class AEthersnatchEffect extends OneShotEffect {
    
    AEthersnatchEffect() {
        super(Outcome.GainControl);
        this.staticText = "Gain control of target spell. You may choose new targets for it";
    }
    
    AEthersnatchEffect(final AEthersnatchEffect effect) {
        super(effect);
    }
    
    @Override
    public AEthersnatchEffect copy() {
        return new AEthersnatchEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = game.getStack().getSpell(this.getTargetPointer().getFirst(game, source));
        if (controller != null && spell != null) {
            spell.setControllerId(controller.getId());
            spell.chooseNewTargets(game, controller.getId(), false, false, null);
            return true;
        }
        return false;
    }
}
