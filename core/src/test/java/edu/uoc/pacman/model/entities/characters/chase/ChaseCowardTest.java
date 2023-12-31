package edu.uoc.pacman.model.entities.characters.chase;

import edu.uoc.pacman.model.entities.characters.ghosts.Ghost;
import edu.uoc.pacman.model.entities.characters.ghosts.chase.ChaseBehaviour;
import edu.uoc.pacman.model.entities.characters.ghosts.chase.ChaseCoward;
import edu.uoc.pacman.model.utils.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ChaseCowardTest {

    @Test
    @Tag("sanity")
    @DisplayName("Sanity ChaseCoward - Class definition")
    void checkClassSanity() {
        final Class<ChaseCoward> ownClass = ChaseCoward.class;

        //Class declaration
        int modifiers = ownClass.getModifiers();
        assertTrue(Modifier.isPublic(modifiers));
        assertFalse(Modifier.isStatic(modifiers));
        assertFalse(Modifier.isFinal(modifiers));
        assertFalse(Modifier.isAbstract(modifiers));

        assertEquals("edu.uoc.pacman.model.entities.characters.ghosts.chase",ownClass.getPackageName());
        assertTrue(ChaseBehaviour.class.isAssignableFrom(ownClass));
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity ChaseCoward - Fields definition")
    public void checkFieldsSanity() {
        final Class<ChaseCoward> ownClass = ChaseCoward.class;

        //check attribute fields
        assertEquals(1, ownClass.getDeclaredFields().length);

        try {
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("TILES_TO_CHASE").getModifiers()));
            assertTrue(Modifier.isStatic(ownClass.getDeclaredField("TILES_TO_CHASE").getModifiers()));
            assertTrue(Modifier.isFinal(ownClass.getDeclaredField("TILES_TO_CHASE").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredField("TILES_TO_CHASE").getType());
        } catch (NoSuchFieldException e) {
            fail("[ERROR] There is some problem with the definition of the attributes");
            e.printStackTrace();
        }
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity ChasePatrol - Methods definition")
    public void checkMethodsSanity() {
        final Class<ChaseCoward> ownClass = ChaseCoward.class;

        //check constructors
        assertEquals(1, ownClass.getDeclaredConstructors().length);

        try {
            //Constructor
            int constructorModifiers = ownClass.getDeclaredConstructor().getModifiers();
            assertTrue(Modifier.isPublic(constructorModifiers));
            assertFalse(Modifier.isStatic(constructorModifiers));
            assertFalse(Modifier.isFinal(constructorModifiers));
        } catch (NoSuchMethodException e) {
            fail("[ERROR] ChaseCoward's constructor is defined wrongly");
        }

        //Max public methods: 6 methods
        assertEquals(1, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isPublic(p.getModifiers())).count());
        //Max protected methods
        assertEquals(0, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isProtected(p.getModifiers())).count());
        //Max package-private methods
        assertEquals(0, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isNative(p.getModifiers())).count());
        //Min private methods: 0
        assertEquals(0, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isPrivate(p.getModifiers())).count());

        try {
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getChasePosition", Ghost.class).getModifiers()));
            assertEquals(Position.class, ownClass.getDeclaredMethod("getChasePosition", Ghost.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of methods");
            e.printStackTrace();
        }
    }

}
