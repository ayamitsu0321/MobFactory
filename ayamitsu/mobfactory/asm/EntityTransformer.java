package ayamitsu.mobfactory.asm;

import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import cpw.mods.fml.relauncher.IClassTransformer;

public class EntityTransformer implements IClassTransformer, Opcodes {

	// TODO: obfuscate
	private static final String ENTITY_CLASS_NAME = "lq";//"net.minecraft.entity.Entity";

	@Override
	public byte[] transform(String name, byte[] bytes) {
		if (!name.equals(ENTITY_CLASS_NAME)) {
			return bytes;
		}

		try {
			bytes = this.hookDoBlockCollisions(bytes);
		} catch (Exception e) {
			throw new RuntimeException("Failed to load:MobFactoryCorePlugin", e);
		}

		return bytes;
	}

	private byte[] hookDoBlockCollisions(byte[] bytes) {
		ClassNode cnode = new ClassNode();
		ClassReader reader = new ClassReader(bytes);
		reader.accept(cnode, 0);

		String targetMethodName = "doBlockCollisions";
		targetMethodName = "D";
		String targetMethoddesc = "()V";
		MethodNode mnode = null;

		for (MethodNode curMnode : (List<MethodNode>)cnode.methods) {
			if (targetMethodName.equals(curMnode.name) && targetMethoddesc.equals(curMnode.desc)) {
				mnode = curMnode;
				break;
			}
		}

		if (mnode != null) {
			LocalVariableNode lvnode = null;

			for (LocalVariableNode lnode : (List<LocalVariableNode>)mnode.localVariables) {
				if (lnode.name.equals("this")) {
					lvnode = lnode;
					break;
				}
			}

			if (lvnode != null) {
				InsnList overrideList = new InsnList();
				overrideList.add(new VarInsnNode(ALOAD, lvnode.index));
				overrideList.add(new MethodInsnNode(INVOKESTATIC, "ayamitsu/mobfactory/registry/EntityHooks", "doBlockCollisions", "(Llq;)V"));

				mnode.instructions.insert(mnode.instructions.get(1), overrideList);

				ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
				cnode.accept(cw);
				bytes = cw.toByteArray();
			}
		}

		return bytes;
	}
}
