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
package mage.sets.ravnica;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public class LurkingInformant extends CardImpl {

    public LurkingInformant(UUID ownerId) {
        super(ownerId, 249, "Lurking Informant", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{U/B}");
        this.expansionSetCode = "RAV";
        this.subtype.add("Human");
        this.subtype.add("Rogue");
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // <i>({UB} can be paid with either {U} or {B}.)</i>
        // {2}, {tap}: Look at the top card of target player's library. You may put that card into that player's graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LurkingInformantEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public LurkingInformant(final LurkingInformant card) {
        super(card);
    }

    @Override
    public LurkingInformant copy() {
        return new LurkingInformant(this);
    }
}

class LurkingInformantEffect extends OneShotEffect {

    public LurkingInformantEffect() {
        super(Outcome.Detriment);
        staticText = "Look at the top card of target player's library. You may put that card into his or her graveyard";
    }

    public LurkingInformantEffect(final LurkingInformantEffect effect) {
        super(effect);
    }

    @Override
    public LurkingInformantEffect copy() {
        return new LurkingInformantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        if (controller != null && player != null) {
            Card card = player.getLibrary().getFromTop(game);
            if (card != null) {
                Cards cards = new CardsImpl();
                cards.add(card);
                controller.lookAtCards("Lurking Informant", cards, game);
                if (controller.chooseUse(outcome, "Do you wish to put card into the player's graveyard?", source, game)) {
                    controller.moveCardToGraveyardWithInfo(card, source.getSourceId(), game, Zone.LIBRARY);
                } else {
                    game.informPlayers(controller.getLogName() + " puts the card back on top of the library.");
                }
                return true;
            }
        }
        return false;
    }

}