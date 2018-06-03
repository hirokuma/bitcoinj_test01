package com.hiro99ma.blogpost.bitcoinj_test01;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.TestNet3Params;

import java.io.File;

class BitcoinjTest {
    private NetworkParameters params;
    private WalletAppKit wak;

    BitcoinjTest() {
        params = TestNet3Params.get();
        wak = new WalletAppKit(params, new File("./wallet"), "wak") {
            @Override
            protected void onSetupCompleted() {
                if (wallet().getKeyChainGroupSize() < 1) {
                    wallet().importKey(new ECKey());
                }
            }
        };
        wak.startAsync();
    }

    void stop() {
        wak.stopAsync();
        wak.awaitTerminated();
    }

    void exec() {
        wak.awaitRunning();
        Address addr = wak.wallet().currentReceiveAddress();
        System.out.println("addr = " + addr.toString());
    }
}

public class Main {
    public static void main(String[] args) {
        BitcoinjTest bjt = new BitcoinjTest();
        bjt.exec();
        bjt.stop();
    }
}
