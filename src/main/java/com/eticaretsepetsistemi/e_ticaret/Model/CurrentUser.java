package com.eticaretsepetsistemi.e_ticaret.Model;

public class CurrentUser {
    private static Kullanici currentUser;

    private CurrentUser() {} // Dışarıdan örnek oluşturulmasını engelle

    public static void setUser(Kullanici user) {
        currentUser = user;
    }

    public static Kullanici getUser() {
        return currentUser;
    }

    public static void clearUser() {
        currentUser = null;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static int getUserId() {
        return currentUser != null ? currentUser.getKullaniciID() : -1;
    }

    public static String getFullName() {
        return currentUser != null ?
                currentUser.getAd() + " " + currentUser.getSoyad() :
                "Misafir";
    }
}