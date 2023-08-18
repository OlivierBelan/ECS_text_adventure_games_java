package libs.character;

import engine.Manager.*;
import libs.character.components.C_Character;
import libs.character.components.C_Bot;
import libs.character.components.C_Player;
import libs.character.systemes.S_CreateCharacter;

public class CharacterFactory extends AFactory {
    public CharacterFactory() {
    }

    @Override
    public void init() {
    }

    @Override
    public void initComponents() {
        this.components.add(new C_Player());
        this.components.add(new C_Bot());
        this.components.add(new C_Character());
    }

    @Override
    public void initSystemes() {
        this.systems.add(new S_CreateCharacter());
    }
}
