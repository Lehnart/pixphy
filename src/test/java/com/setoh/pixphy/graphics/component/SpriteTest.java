package com.setoh.pixphy.graphics.component;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

final class SpriteTest {

    @Test
    void testConstructorSetsX() {
        Sprite sprite = new Sprite(10, 20, "textures/asteroid.png");
        assertEquals(10, sprite.getX());
    }

    @Test
    void testConstructorSetsY() {
        Sprite sprite = new Sprite(10, 20, "textures/asteroid.png");
        assertEquals(20, sprite.getY());
    }

    @Test
    void testConstructorSetsTextureName() {
        Sprite sprite = new Sprite(10, 20, "textures/asteroid.png");
        assertEquals("textures/asteroid.png", sprite.getTextureName());
    }

    @Test
    void testConstructorWithDifferentValues() {
        Sprite sprite = new Sprite(100, 50, "textures/background.png");
        assertEquals(100, sprite.getX());
        assertEquals(50, sprite.getY());
        assertEquals("textures/background.png", sprite.getTextureName());
    }

    @Test
    void testConstructorWithZeroValues() {
        Sprite sprite = new Sprite(0, 0, "textures/empty.png");
        assertEquals(0, sprite.getX());
        assertEquals(0, sprite.getY());
        assertEquals("textures/empty.png", sprite.getTextureName());
    }

    @Test
    void testConstructorWithNegativeValues() {
        Sprite sprite = new Sprite(-5, -10, "textures/negative.png");
        assertEquals(-5, sprite.getX());
        assertEquals(-10, sprite.getY());
        assertEquals("textures/negative.png", sprite.getTextureName());
    }

    @Test
    void testSetXUpdatesValue() {
        Sprite sprite = new Sprite(10, 20, "textures/asteroid.png");
        sprite.setX(42.0);
        assertEquals(42, sprite.getX());
    }

    @Test
    void testSetYUpdatesValue() {
        Sprite sprite = new Sprite(10, 20, "textures/asteroid.png");
        sprite.setY(64.0);
        assertEquals(64, sprite.getY());
    }

    @Test
    void testSetXTruncatesPositiveDouble() {
        Sprite sprite = new Sprite(0, 0, "textures/asteroid.png");
        sprite.setX(12.99);
        assertEquals(12, sprite.getX());
    }

    @Test
    void testSetYTruncatesNegativeDoubleTowardZero() {
        Sprite sprite = new Sprite(0, 0, "textures/asteroid.png");
        sprite.setY(-7.8);
        assertEquals(-7, sprite.getY());
    }
}
