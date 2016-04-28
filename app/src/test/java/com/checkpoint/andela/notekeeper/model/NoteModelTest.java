package com.checkpoint.andela.notekeeper.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by suadahaji.
 */
public class NoteModelTest {

    String test_note_content;
    String test_note_title;
    String test_note_date;
    NoteModel noteModel;

    @Before
    public void setUp() throws Exception {
        noteModel = new NoteModel();
        test_note_title = "Harry Potter";
        test_note_content = "M r. and Mrs. Dursley, of number four, Privet Drive," +
                " were proud to say that they were perfectly normal, thank you very much." +
                "They were the last people you’d expect to be involved in anything strange or mysterious, because they just didn’t hold with such nonsense.";
        test_note_date = "2016/04/28";
    }

    @Test
    public void testNote_title() throws Exception {
        noteModel.setNote_title(test_note_title);
        assertEquals(noteModel.getNote_title(), test_note_title);
        assertNotNull(noteModel);
    }

    @Test
    public void testNote_content() throws Exception {
        noteModel.setNote_content(test_note_content);
        assertEquals(noteModel.getNote_content(), test_note_content);
        assertNotNull(noteModel);
    }

    @Test
    public void testNote_date() throws Exception {
        noteModel.setNote_date();
        assertTrue(noteModel.getNote_date().contains("2016"));
    }


    @Test
    public void testGetNoteTrashed() throws Exception {
        noteModel.setNoteTrashed("no");
        assertTrue(noteModel.getNoteTrashed().contains("no"));
    }

}