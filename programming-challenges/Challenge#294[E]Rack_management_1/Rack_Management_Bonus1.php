<?php
    $tiles = "a??????";
    $wantedWord = "program";
        
    function checkWord($tiles, $wantedWord) {
        for($i = 0; $i < strlen($wantedWord); $i++) {
            $letterOk = false;
            for($x = 0; $x < strlen($tiles); $x++) {
                if((($tiles[$x] == $wantedWord[$i]) || $tiles[$x] == "?") && !$letterOk) { //Third check it's to only do one letter at a time.
                    $letterOk = true;
                    $tiles[$x] = "_";
                }
            }
            if(!$letterOk) return false;
        }
        return true;
    }

    if(checkWord($tiles, $wantedWord)) {
        echo "It is Possible.";
    } else {
        echo "It is NOT.";
    }
?>