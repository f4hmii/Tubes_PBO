package com.movr.controller.action;

// Interface ini mendefinisikan sebuah aksi yang bisa dieksekusi.
public interface ProductAction {
    /**
     * Menjalankan aksi.
     * @return true jika aksi berhasil, false jika gagal.
     */
    boolean execute();
}