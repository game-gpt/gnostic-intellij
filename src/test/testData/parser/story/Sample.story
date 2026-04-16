# 星光学院的秘密 - 主线剧本

% let player_name = "主角"
% let sakura_affection = 0

% include chapter1_school.story

== prologue ==

%audio::play("bgm/mystery_theme.mp3", 0.6)
%scene::change("bg/school_gate_sunset.png", "fade", 1.5)

星光学院，这所历史悠久的名校，隐藏着不为人知的秘密。

樱井美咲：新同学！你在看什么呢？

* [好啊，我去看看]
    主角：好啊，我去看看！
    ~ %{sakura_affection++}
    -> accept_student_council

* [抱歉，我还有事]
    主角：抱歉，我还有事。
    -> decline_student_council

== accept_student_council ==

樱井美咲：太好了！那放学后见！

-> after_school_choice

== after_school_choice ==

{
    - sakura_affection >= 5:
        樱井美咲：其实...我有话想对你说。
        -> special_route
    - else:
        樱井美咲：再见！
        -> normal_route
}

---

== special_route ==

%character::show("sakura", "blush", "center")
%effect::sparkle(1.5)

樱井美咲：我一直想告诉你...

* {sakura_affection >= 8} [深入了解]
    樱井美咲：其实...我有话想对你说。
    -> sakura_true_ending

* [微笑回应]
    主角：谢谢你告诉我。
    -> sakura_good_ending

-> DONE
