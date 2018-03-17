package com.apptastic.insynsregistret;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class InsynsregistretTest {
    private BufferedReader reader;
    private Insynsregistret msMock;


    @Before
    public void setupTest() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        reader = TestUtil.getExportedTransactionFile(classLoader, "insynSample1.csv");

        msMock = spy(Insynsregistret.class);
        doReturn(reader).when(msMock).doSearch(anyString());
    }


    @After
    public void teardownTest() throws IOException {
        if (reader != null)
            reader.close();
    }


    @Test
    public void queryByTransactionDate() throws IOException {
        Date from = new GregorianCalendar(2018, Calendar.MARCH,1).getTime();
        Date to = new GregorianCalendar(2018, Calendar.MARCH,1).getTime();

        Query query = TransactionQueryBuilder.instance()
                .fromTransactionDate(from)
                .toTransactionDate(to)
                .build();

        long transactionCount = msMock.search(query).count();
        assertEquals(375, transactionCount);
    }


    @Test(expected = IllegalArgumentException.class)
    public void badTransactionFromDate() throws IOException {
        Date from = null;
        Date to = new GregorianCalendar(2018, Calendar.MARCH,1).getTime();

        Query query = TransactionQueryBuilder.instance()
                .fromTransactionDate(from)
                .toTransactionDate(to)
                .build();

        long transactionCount = msMock.search(query).count();
        assertEquals(375, transactionCount);
    }


    @Test(expected = IllegalArgumentException.class)
    public void badTransactionToDate() throws IOException {
        Date from = new GregorianCalendar(2018, Calendar.MARCH,1).getTime();
        Date to = null;

        Query query = TransactionQueryBuilder.instance()
                .fromTransactionDate(from)
                .toTransactionDate(to)
                .build();

        long transactionCount = msMock.search(query).count();
        assertEquals(375, transactionCount);
    }


    @Test
    public void queryByPublicationDate() throws IOException {
        Date from = new GregorianCalendar(2018, Calendar.MARCH,1).getTime();
        Date to = new GregorianCalendar(2018, Calendar.MARCH,1).getTime();

        Query query = TransactionQueryBuilder.instance()
                .fromPublicationDate(from)
                .toPublicationDate(to)
                .build();

        long transactionCount = msMock.search(query).count();
        assertEquals(375, transactionCount);
    }


    @Test(expected = IllegalArgumentException.class)
    public void badPublicationFromDate() throws IOException {
        Date from = null;
        Date to = new GregorianCalendar(2018, Calendar.MARCH,1).getTime();

        Query query = TransactionQueryBuilder.instance()
                .fromPublicationDate(from)
                .toPublicationDate(to)
                .build();

        long transactionCount = msMock.search(query).count();
        assertEquals(375, transactionCount);
    }


    @Test(expected = IllegalArgumentException.class)
    public void badPublicationToDate() throws IOException {
        Date from = new GregorianCalendar(2018, Calendar.MARCH,1).getTime();
        Date to = null;

        Query query = TransactionQueryBuilder.instance()
                .fromPublicationDate(from)
                .toPublicationDate(to)
                .build();

        long transactionCount = msMock.search(query).count();
        assertEquals(375, transactionCount);
    }


    @Test
    public void validateTransaction() throws IOException {
        Date from = new GregorianCalendar(2018, Calendar.MARCH,1).getTime();
        Date to = new GregorianCalendar(2018, Calendar.MARCH,1).getTime();

        Query query = TransactionQueryBuilder.instance()
                .fromPublicationDate(from)
                .toPublicationDate(to)
                .build();

        Optional<Transaction> firstTransaction = msMock.search(query).findFirst();
        assertTrue(firstTransaction.isPresent());

        Transaction transaction = firstTransaction.get();

        assertEquals("2018-03-11 12:38:01", transaction.getPublicationDate());
        assertEquals("Empir Group AB", transaction.getIssuer());
        assertEquals("", transaction.getLeiCode());
        assertEquals("Alfanode AB", transaction.getNotifier());
        assertEquals("Alfanode AB", transaction.getPersonDischargingManagerialResponsibilities());
        assertEquals("VD", transaction.getPosition());
        assertEquals(false, transaction.isCloselyAssociated());
        assertEquals(false, transaction.isAmendment());
        assertEquals("", transaction.getDetailsOfAmendment());
        assertEquals(true, transaction.isInitialNotification());
        assertEquals(false, transaction.isLinkedToShareOptionProgramme());
        assertEquals("Förvärv", transaction.getNatureOfTransaction());
        assertEquals("Empir Group AB", transaction.getInstrument());
        assertEquals("SE0010769182", transaction.getIsin());
        assertEquals("2018-03-11 00:00:00", transaction.getTransactionDate());
        assertEquals(28227, transaction.getQuantity(), 0.0);
        assertEquals("Antal", transaction.getUnit());
        assertEquals(37.9, transaction.getPrice(), 0.0);
        assertEquals("SEK", transaction.getCurrency());
        assertEquals("Utanför handelsplats", transaction.getTradingVenue());
        assertEquals("Aktuell", transaction.getStatus());
    }


    @Test
    public void badPricePointInsteadOfComma() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        reader = TestUtil.getExportedTransactionFile(classLoader, "pricePointInsteadOfComma.csv");

        msMock = spy(Insynsregistret.class);
        doReturn(reader).when(msMock).doSearch(anyString());


        Date from = new GregorianCalendar(2018, Calendar.MARCH,1).getTime();
        Date to = new GregorianCalendar(2018, Calendar.MARCH,1).getTime();

        Query query = TransactionQueryBuilder.instance()
                .fromPublicationDate(from)
                .toPublicationDate(to)
                .build();

        Optional<Transaction> firstTransaction = msMock.search(query).findFirst();
        assertTrue(firstTransaction.isPresent());

        Transaction transaction = firstTransaction.get();

        assertEquals("2018-03-11 12:38:01", transaction.getPublicationDate());
        assertEquals("Empir Group AB", transaction.getIssuer());
        assertEquals("", transaction.getLeiCode());
        assertEquals("Alfanode AB", transaction.getNotifier());
        assertEquals("Alfanode AB", transaction.getPersonDischargingManagerialResponsibilities());
        assertEquals("VD", transaction.getPosition());
        assertEquals(false, transaction.isCloselyAssociated());
        assertEquals(false, transaction.isAmendment());
        assertEquals("", transaction.getDetailsOfAmendment());
        assertEquals(true, transaction.isInitialNotification());
        assertEquals(false, transaction.isLinkedToShareOptionProgramme());
        assertEquals("Förvärv", transaction.getNatureOfTransaction());
        assertEquals("Empir Group AB", transaction.getInstrument());
        assertEquals("SE0010769182", transaction.getIsin());
        assertEquals("2018-03-11 00:00:00", transaction.getTransactionDate());
        assertEquals(28227, transaction.getQuantity(), 0.0);
        assertEquals("Antal", transaction.getUnit());
        assertEquals(37.9, transaction.getPrice(), 0.0);
        assertEquals("SEK", transaction.getCurrency());
        assertEquals("Utanför handelsplats", transaction.getTradingVenue());
        assertEquals("Aktuell", transaction.getStatus());
    }


    @Test
    public void badPrice() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        reader = TestUtil.getExportedTransactionFile(classLoader, "badPrice.csv");

        msMock = spy(Insynsregistret.class);
        doReturn(reader).when(msMock).doSearch(anyString());


        Date from = new GregorianCalendar(2018, Calendar.MARCH,1).getTime();
        Date to = new GregorianCalendar(2018, Calendar.MARCH,1).getTime();

        Query query = TransactionQueryBuilder.instance()
                .fromPublicationDate(from)
                .toPublicationDate(to)
                .build();

        long transactionCount = msMock.search(query).count();
        assertEquals(1, transactionCount);
    }


    @Test
    public void badQuantity() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        reader = TestUtil.getExportedTransactionFile(classLoader, "badQuantity.csv");

        msMock = spy(Insynsregistret.class);
        doReturn(reader).when(msMock).doSearch(anyString());


        Date from = new GregorianCalendar(2018, Calendar.MARCH,1).getTime();
        Date to = new GregorianCalendar(2018, Calendar.MARCH,1).getTime();

        Query query = TransactionQueryBuilder.instance()
                .fromPublicationDate(from)
                .toPublicationDate(to)
                .build();

        long transactionCount = msMock.search(query).count();
        assertEquals(1, transactionCount, 0.0);
    }


    @Test
    public void emptyDownload() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        reader = TestUtil.getExportedTransactionFile(classLoader, "empty.csv");

        msMock = spy(Insynsregistret.class);
        doReturn(reader).when(msMock).doSearch(anyString());


        Date from = new GregorianCalendar(2018, Calendar.MARCH,1).getTime();
        Date to = new GregorianCalendar(2018, Calendar.MARCH,1).getTime();

        Query query = TransactionQueryBuilder.instance()
                .fromPublicationDate(from)
                .toPublicationDate(to)
                .issuer("Examples")
                .build();

        Optional<Transaction> firstTransaction = msMock.search(query).findFirst();
        assertFalse(firstTransaction.isPresent());
    }


    @Test
    public void noTransactions() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        reader = TestUtil.getExportedTransactionFile(classLoader, "noTransactions.csv");

        msMock = spy(Insynsregistret.class);
        doReturn(reader).when(msMock).doSearch(anyString());


        Date from = new GregorianCalendar(2018, Calendar.MARCH,1).getTime();
        Date to = new GregorianCalendar(2018, Calendar.MARCH,1).getTime();

        Query query = TransactionQueryBuilder.instance()
                .fromPublicationDate(from)
                .toPublicationDate(to)
                .personDischargingManagerialResponsibilities("dummy")
                .build();

        Optional<Transaction> firstTransaction = msMock.search(query).findFirst();
        assertFalse(firstTransaction.isPresent());
    }


    @Test
    public void liveSvQueryByTransactionDate() throws IOException {
        Insynsregistret ms = new Insynsregistret();

        Query transactionQuery = TransactionQueryBuilder.transactionsPastXDays(10)
                .build();

        long transactionCount = ms.search(transactionQuery).count();
        assertTrue(transactionCount > 0);
    }


    @Test
    public void liveSvQueryByPublicationDate() throws IOException {
        Insynsregistret ms = new Insynsregistret();

        Query transactionQuery = TransactionQueryBuilder.publicationsPastXDays(10)
                .build();

        long transactionCount = ms.search(transactionQuery).count();
        assertTrue(transactionCount > 0);
    }


    @Test
    public void liveEnQueryByTransactionDate() throws IOException {
        Insynsregistret ms = new Insynsregistret();

        Query transactionQuery = TransactionQueryBuilder.transactionsPastXDays(10)
                .language(Language.ENGLISH)
                .build();

        long transactionCount = ms.search(transactionQuery).count();
        assertTrue(transactionCount > 0);
    }


    @Test
    public void liveEnQueryByPublicationDate() throws IOException {
        Insynsregistret ms = new Insynsregistret();

        Query transactionQuery = TransactionQueryBuilder.publicationsPastXDays(10)
                .language(Language.ENGLISH)
                .build();

        long transactionCount = ms.search(transactionQuery).count();
        assertTrue(transactionCount > 0);
    }

}
