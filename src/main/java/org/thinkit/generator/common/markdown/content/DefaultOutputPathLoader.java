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

package org.thinkit.generator.common.markdown.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.thinkit.common.catalog.Platform;
import org.thinkit.framework.content.Attribute;
import org.thinkit.framework.content.Condition;
import org.thinkit.framework.content.Content;
import org.thinkit.framework.content.annotation.ContentMapping;
import org.thinkit.generator.common.markdown.content.entity.DefaultOutputPath;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * 既定の出力先パスを管理するクラスです。
 * <p>
 * {@link #execute()} を実行することでコンテンツ「既定出力先」から既定の出力先を生成する際に必要な情報を取得します。
 * <p>
 * 実行の前提としてプログラム実行時のプラットフォームに対応した既定の出力先がコンテンツ「既定出力先」に定義されている必要があります。
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 */
@ToString
@EqualsAndHashCode
@ContentMapping(content = "generator/markdown/DefaultOutputPath")
public final class DefaultOutputPathLoader implements Content<DefaultOutputPath> {

    /**
     * プログラム実行時のプラットフォーム要素
     */
    @Getter(AccessLevel.PRIVATE)
    private Platform platform;

    /**
     * デフォルトコンストラクタ
     */
    private DefaultOutputPathLoader() {
    }

    /**
     * コンストラクタ
     *
     * @param platform プログラム実行時のプラットフォーム要素
     * @exception NullPointerException 引数として {@code null} が渡された場合
     */
    private DefaultOutputPathLoader(@NonNull Platform platform) {
        this.platform = platform;
    }

    /**
     * 引数として与えられた {@code platform} をもとに {@link DefaultOutputPathLoader}
     * クラスの新しいインスタンスを生成し返却します。
     *
     * @param platform プログラム実行時のプラットフォーム
     * @return {@link DefaultOutputPathLoader} クラスの新しいインスタンス
     *
     * @exception NullPointerException 引数として {@code null} が渡された場合
     * @see Platform
     */
    public static Content<DefaultOutputPath> of(@NonNull Platform platform) {
        return new DefaultOutputPathLoader(platform);
    }

    /**
     * コンテンツ要素定数
     */
    private enum ContentAttribute implements Attribute {
        環境変数名, 出力先ディレクトリ;

        @Override
        public String getString() {
            return this.name();
        }
    }

    /**
     * コンテンツ条件定数
     */
    private enum ContentConditions implements Condition {
        プラットフォームコード;

        @Override
        public String getString() {
            return this.name();
        }
    }

    @Override
    public DefaultOutputPath execute() {

        final Map<String, String> content = loadContent(this.getClass()).get(0);

        return DefaultOutputPath.of(content.get(ContentAttribute.環境変数名.getString()),
                content.get(ContentAttribute.出力先ディレクトリ.getString()));
    }

    @Override
    public List<Attribute> getAttributes() {
        final List<Attribute> attributes = new ArrayList<>(2);
        attributes.add(ContentAttribute.環境変数名);
        attributes.add(ContentAttribute.出力先ディレクトリ);

        return attributes;
    }

    @Override
    public Map<Condition, String> getConditions() {
        final Map<Condition, String> conditions = new HashMap<>(1);
        conditions.put(ContentConditions.プラットフォームコード, String.valueOf(this.getPlatform().getCode()));

        return conditions;
    }
}
