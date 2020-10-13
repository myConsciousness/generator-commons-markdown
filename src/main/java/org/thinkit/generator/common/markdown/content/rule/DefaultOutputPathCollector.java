/*
 * Copyright 2020 Kato Shinya.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.thinkit.generator.common.markdown.content.rule;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.thinkit.common.catalog.Platform;
import org.thinkit.framework.content.ContentInvoker;
import org.thinkit.framework.content.rule.Rule;
import org.thinkit.generator.common.markdown.content.DefaultOutputPathLoader;
import org.thinkit.generator.common.markdown.content.entity.DefaultOutputPath;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * プログラム実行時のプラットフォームに応じた既定出力先を取得する処理を定義したルールクラスです。
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 */
@ToString
@EqualsAndHashCode
public final class DefaultOutputPathCollector implements Rule<DefaultOutputPath> {

    /**
     * プログラム実行時のプラットフォーム要素
     */
    private Platform platform;

    /**
     * デフォルトコンストラクタ
     */
    private DefaultOutputPathCollector() {
    }

    /**
     * コンストラクタ
     *
     * @param platform プログラム実行時のプラットフォーム要素
     * @exception NullPointerException 引数として {@code null} が渡された場合
     */
    private DefaultOutputPathCollector(@NonNull Platform platform) {
        this.platform = platform;
    }

    /**
     * 引数として与えられた {@code platform} をもとに {@link DefaultOutputPathCollector}
     * クラスの新しいインスタンスを生成し返却します。
     *
     * @param platform プログラム実行時のプラットフォーム
     * @return {@link DefaultOutputPathCollector} クラスの新しいインスタンス
     *
     * @exception NullPointerException 引数として {@code null} が渡された場合
     * @see Platform
     */
    public static Rule<DefaultOutputPath> of(@NonNull Platform platform) {
        return new DefaultOutputPathCollector(platform);
    }

    @Override
    public DefaultOutputPath execute() {
        return ContentInvoker.of(DefaultOutputPathLoader.of(platform)).invoke();
    }
}