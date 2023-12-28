package com.hanifsapp.hisabee;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Toast;

import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;
import com.dantsu.escposprinter.textparser.PrinterTextParserImg;

public class printEpos {

    public static void generatePdf(View view, Context context) throws EscPosEncodingException, EscPosBarcodeException, EscPosParserException, EscPosConnectionException {
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        startPrint(bitmap, context);
    }






    private static void startPrint(Bitmap bitmap, Context context) throws EscPosConnectionException, EscPosEncodingException, EscPosBarcodeException, EscPosParserException {

        try {
            EscPosPrinter printer = new EscPosPrinter(BluetoothPrintersConnections.selectFirstPaired(), 203, 48f, 32);
            printer
                    .printFormattedText(
                            "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer, bitmap)+"</img>\n"
                    );
        }catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
